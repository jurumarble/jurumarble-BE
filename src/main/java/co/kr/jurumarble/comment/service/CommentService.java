package co.kr.jurumarble.comment.service;

import co.kr.jurumarble.client.tourApi.RestaurantInfoDto;
import co.kr.jurumarble.comment.domain.Comment;
import co.kr.jurumarble.comment.enums.Emotion;
import co.kr.jurumarble.comment.repository.CommentRepository;
import co.kr.jurumarble.comment.service.request.CreateCommentServiceRequest;
import co.kr.jurumarble.comment.service.request.GetCommentServiceRequest;
import co.kr.jurumarble.comment.service.request.UpdateCommentServiceRequest;
import co.kr.jurumarble.comment.service.request.UpdateRestaurantServiceRequest;
import co.kr.jurumarble.exception.comment.CommentNotFoundException;
import co.kr.jurumarble.exception.comment.InvalidSortingMethodException;
import co.kr.jurumarble.exception.user.UserNotFoundException;
import co.kr.jurumarble.exception.vote.VoteNotFoundException;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.repository.UserRepository;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
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
    private final CommentRepository commentRepository;
    private final CommentEmoteManager commentEmoteManager;
    private final TourApiDataManager tourApiDataManager;
    private final CommentValidator commentValidator;

    @Transactional
    public void createComment(Long voteId, Long userId, CreateCommentServiceRequest request) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        Comment parentComment = commentValidator.checkParentComment(request);
        commentValidator.checkNestedCommentAllowed(parentComment);
        commentValidator.validateCommentBelongsToVote(parentComment, vote);
        Comment comment = request.toComment(parentComment, user, voteId);

        commentRepository.save(comment);

    }

    public Slice<GetCommentData> getComments(Long voteId, GetCommentServiceRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        List<Comment> comments = findCommentsBySortType(voteId, request, pageable); //정렬방식에 따라 Pageable 적용하여 부모댓글 가져오기
        getChildCommentByParentComment(comments);  //부모댓글에 속한 대댓글들 다가져오기
        List<GetCommentData> getCommentData = convertToCommentDataList(comments); // 댓글 목록을 매개 변수로 받아, GetCommentData 목록을 반환
        Slice<GetCommentData> slice = convertToSlice(voteId, request, pageable, getCommentData); // Response 리스트를 Slice 객체로 만들어주기

        return slice;

    }

    @Transactional
    public void updateComment(Long voteId, Long commentId, Long userId, UpdateCommentServiceRequest request) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        commentValidator.validateCommentBelongsToUser(comment, user);
        commentValidator.validateCommentBelongsToVote(comment, vote);
        comment.updateContent(request.getContent());
    }


    @Transactional
    public void deleteComment(Long voteId, Long commentId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        commentValidator.validateCommentBelongsToUser(comment, user);
        commentValidator.validateCommentBelongsToVote(comment, vote);
        commentRepository.delete(comment);
    }

    @Transactional
    public void emoteComment(Long voteId, Long commentId, Long userId, Emotion emotion) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        commentEmoteManager.doEmote(emotion, user, comment);
    }

    @Transactional
    public void addRestaurantToComment(Long voteId, Long commentId, Long userId, UpdateRestaurantServiceRequest request) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        commentValidator.validateCommentBelongsToUser(comment, user);
        comment.updateRestaurant(request);

    }

    public List<SearchRestaurantData> searchRestaurant(Long voteId, Long commentId, Long userId, String keyword, Integer areaCode, int page) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        //vote의 지역이 있으면 받아오고 없으면 선택받은 지역
        List<RestaurantInfoDto> restaurantInfo = tourApiDataManager.getRestaurantInfoList(keyword, areaCode, page);

        return tourApiDataManager.convertToSearchRestaurantDataList(restaurantInfo);

    }

    public List<String> getRestaurantImage(Long voteId, Long commentId, Long userId, String contentId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        List<String> detailImages = tourApiDataManager.fetchDetailImages(contentId);

        return detailImages;

    }

    private List<Comment> findCommentsBySortType(Long voteId, GetCommentServiceRequest request, Pageable pageable) {
        List<Comment> comments;
        switch (request.getSortBy()) {
            case ByTime:
                comments = commentRepository.findNewestComments(voteId, pageable);
                break;
            case ByPopularity:
                comments = commentRepository.findHotComments(voteId, pageable);
                break;
            default:
                throw new InvalidSortingMethodException();
        }
        return comments;
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

