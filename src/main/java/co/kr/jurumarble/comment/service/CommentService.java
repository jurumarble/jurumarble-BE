package co.kr.jurumarble.comment.service;

import co.kr.jurumarble.client.tourApi.RestaurantInfoDto;
import co.kr.jurumarble.comment.domain.Comment;
import co.kr.jurumarble.comment.enums.CommentType;
import co.kr.jurumarble.comment.enums.Emotion;
import co.kr.jurumarble.comment.enums.Region;
import co.kr.jurumarble.comment.repository.CommentRepository;
import co.kr.jurumarble.comment.service.request.CreateCommentServiceRequest;
import co.kr.jurumarble.comment.service.request.GetCommentServiceRequest;
import co.kr.jurumarble.comment.service.request.UpdateCommentServiceRequest;
import co.kr.jurumarble.comment.service.request.UpdateRestaurantServiceRequest;
import co.kr.jurumarble.drink.repository.DrinkRepository;
import co.kr.jurumarble.exception.comment.CommentNotFoundException;
import co.kr.jurumarble.exception.comment.InvalidCommentTypeException;
import co.kr.jurumarble.exception.comment.InvalidSortingMethodException;
import co.kr.jurumarble.exception.user.UserNotFoundException;
import co.kr.jurumarble.exception.vote.VoteNotFoundException;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.repository.UserRepository;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;
    private final DrinkRepository drinkRepository;
    private final CommentRepository commentRepository;
    private final CommentEmoteManager commentEmoteManager;
    private final TourApiDataManager tourApiDataManager;
    private final CommentValidator commentValidator;

    @Transactional
    public void createComment(CommentType commentType, Long typeId, Long userId, CreateCommentServiceRequest request) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Comment parentComment = commentValidator.checkParentComment(request);
        commentValidator.checkNestedCommentAllowed(parentComment);
        commentValidator.validateCommentBelongsToType(commentType, typeId, parentComment);
        Comment comment = request.toComment(commentType, parentComment, user, typeId);
        commentRepository.save(comment);

    }


    public Slice<GetCommentData> getComments(CommentType commentType, Long typeId, GetCommentServiceRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        List<Comment> comments = findCommentsBySortType(commentType, typeId, request, pageable); //정렬방식에 따라 부모댓글 가져오기
        getChildCommentByParentComment(comments);
        List<GetCommentData> getCommentData = convertToCommentDataList(comments); // 댓글 목록을 매개 변수로 받아, GetCommentData 목록을 반환
        Slice<GetCommentData> slice = convertToSlice(typeId, request, pageable, getCommentData); // Response 리스트를 Slice 객체로 만들어주기

        return slice;

    }

    @Transactional
    public void updateComment(CommentType commentType, Long typeId, Long commentId, Long userId, UpdateCommentServiceRequest request) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        commentValidator.validateCommentBelongsToUser(comment, user);
        commentValidator.validateCommentBelongsToType(commentType, typeId, comment);
        comment.updateContent(request.getContent());
    }


    @Transactional
    public void deleteComment(CommentType commentType, Long typeId, Long commentId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        commentValidator.validateCommentBelongsToUser(comment, user);
        commentValidator.validateCommentBelongsToType(commentType, typeId, comment);
        commentRepository.delete(comment);
    }

    @Transactional
    public void emoteComment(CommentType commentType, Long typeId, Long commentId, Long userId, Emotion emotion) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        commentValidator.validateCommentBelongsToType(commentType, typeId, comment);
        commentEmoteManager.doEmote(emotion, user, comment);
    }

    @Transactional
    public void addRestaurantToComment(CommentType commentType, Long typeId, Long commentId, Long userId, UpdateRestaurantServiceRequest request) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        commentValidator.validateCommentBelongsToUser(comment, user);
        commentValidator.validateCommentBelongsToType(commentType, typeId, comment);
        comment.updateRestaurant(request);

    }

    @Cacheable(value = "searchRestaurant", key = "(#keyword ?: '') + '_' + (#region?.getCode() ?: '') + '_' + #page")
    public List<SearchRestaurantData> searchRestaurant(CommentType commentType, Long typeId, Long commentId, Long userId, String keyword, Region region, int page) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        commentValidator.validateCommentBelongsToUser(comment, user);
        commentValidator.validateCommentBelongsToType(commentType, typeId, comment);
        List<RestaurantInfoDto> restaurantInfo = tourApiDataManager.getRestaurantInfoList(keyword, region, page);
        return tourApiDataManager.convertToSearchRestaurantDataList(restaurantInfo);

    }

    @Cacheable(value = "getRestaurantImage", key = "#contentId")
    public List<String> getRestaurantImage(CommentType commentType, Long typeId, Long commentId, Long userId, String contentId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        commentValidator.validateCommentBelongsToUser(comment, user);
        commentValidator.validateCommentBelongsToType(commentType, typeId, comment);
        List<String> detailImages = tourApiDataManager.fetchDetailImages(contentId);

        return detailImages;

    }


    private List<Comment> findCommentsBySortType(CommentType commentType, Long typeId, GetCommentServiceRequest request, Pageable pageable) {
        switch (request.getSortBy()) {
            case ByTime:
                return findNewestComments(commentType, typeId, pageable);
            case ByPopularity:
                return findHotComments(commentType, typeId, pageable);
            default:
                throw new InvalidSortingMethodException();
        }
    }

    private List<Comment> findNewestComments(CommentType commentType, Long typeId, Pageable pageable) {
        if (commentType == CommentType.VOTES) {
            return commentRepository.findNewestVoteComments(typeId, pageable);
        } else if (commentType == CommentType.DRINKS) {
            return commentRepository.findNewestDrinkComments(typeId, pageable);
        } else {
            throw new InvalidCommentTypeException();
        }

    }

    private List<Comment> findHotComments(CommentType commentType, Long typeId, Pageable pageable) {
        if (commentType == CommentType.VOTES) {
            return commentRepository.findHotVoteComments(typeId, pageable);
        } else if (commentType == CommentType.DRINKS) {
            return commentRepository.findHotDrinkComments(typeId, pageable);
        } else {
            throw new InvalidCommentTypeException();
        }
    }

    private void getChildCommentByParentComment(List<Comment> comments) {
        List<Comment> childComments = new ArrayList<>(); //대댓글
        //대댓글 가져오기
        for (Comment parent : comments) {
            childComments.addAll(commentRepository.findByParent(parent));
        }
        comments.addAll(childComments);
    }


    private List<GetCommentData> convertToCommentDataList(List<Comment> parentComments) {
        List<GetCommentData> getCommentData = new ArrayList<>();
        Map<Long, GetCommentData> map = new HashMap<>();

        for (Comment comment : parentComments) {
            GetCommentData response = new GetCommentData(comment);
            map.put(response.getId(), response);

            if (response.getParentId() != null) {
                map.get(response.getParentId()).getChildren().add(response);
            } else {
                getCommentData.add(response);
            }
        }
        return getCommentData;
    }

    private Slice<GetCommentData> convertToSlice(Long voteId, GetCommentServiceRequest request, Pageable pageable, List<GetCommentData> getCommentData) {
        int countComments = commentRepository.countByVoteIdAndParentIsNull(voteId);   //투표에 속한 부모 댓글수 전부 가져오기
        int lastPageNumber = (int) Math.ceil((double) countComments / request.getSize());
        boolean hasNext = request.getPage() < lastPageNumber - 1;

        Slice<GetCommentData> slice = new SliceImpl<>(getCommentData, pageable, hasNext);
        return slice;
    }

}

