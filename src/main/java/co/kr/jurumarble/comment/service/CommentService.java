package co.kr.jurumarble.comment.service;

import co.kr.jurumarble.client.tourApi.RestaurantInfoDto;
import co.kr.jurumarble.client.tourApi.TourApiService;
import co.kr.jurumarble.comment.domain.Comment;
import co.kr.jurumarble.comment.domain.CommentEmotion;
import co.kr.jurumarble.comment.enums.Emotion;
import co.kr.jurumarble.comment.repository.CommentEmotionRepository;
import co.kr.jurumarble.comment.repository.CommentRepository;
import co.kr.jurumarble.comment.service.request.CreateCommentServiceRequest;
import co.kr.jurumarble.comment.service.request.GetCommentServiceRequest;
import co.kr.jurumarble.comment.service.request.UpdateCommentServiceRequest;
import co.kr.jurumarble.comment.service.request.UpdateRestaurantServiceRequest;
import co.kr.jurumarble.exception.comment.CommentNotBelongToUserException;
import co.kr.jurumarble.exception.comment.CommentNotFoundException;
import co.kr.jurumarble.exception.comment.InvalidSortingMethodException;
import co.kr.jurumarble.exception.comment.NestedCommentNotAllowedException;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;
    private final CommentRepository commentRepository;
    private final CommentEmotionRepository commentEmotionRepository;
    private final TourApiService tourApiService;

    @Transactional
    public void createComment(Long voteId, Long userId, CreateCommentServiceRequest request) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        Comment parentComment = checkParentComment(request);  // 부모댓글이 있는지 없는지 확인
        checkNestedCommentAllowed(parentComment);
        Comment comment = new Comment(request, parentComment, user, voteId);

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
        validateCommentBelongsToUser(comment, user);
        comment.updateContent(request);
    }


    @Transactional
    public void deleteComment(Long voteId, Long commentId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        validateCommentBelongsToUser(comment, user);
        commentRepository.delete(comment);
    }

    @Transactional
    public void emoteComment(Long voteId, Long commentId, Long userId, Emotion emotion) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        doEmote(emotion, user, comment);
    }

    @Transactional
    public void addRestaurantToComment(Long voteId, Long commentId, Long userId, UpdateRestaurantServiceRequest request) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        comment.updateRestaurant(request);

    }

    public List<SearchRestaurantData> searchRestaurant(Long voteId, Long commentId, Long userId, String keyword, int page) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        //vote의 지역이 있으면 받아오고 없으면 선택받은 지역

        List<RestaurantInfoDto> restaurantInfo = getRestaurantInfoList(keyword, page);

        return convertToSearchSnackResponseList(restaurantInfo);

    }

    public List<String> getRestaurantImage(Long voteId, Long commentId, Long userId, String contentId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        List<String> detailImages = tourApiService.getDetailImages(contentId);

        return detailImages;

    }

    private void validateCommentBelongsToUser(Comment comment, User user) {
        if (!commentRepository.existsByCommentAndUser(comment, user)) {
            throw new CommentNotBelongToUserException();
        }
    }

    private Comment checkParentComment(CreateCommentServiceRequest request) {
        if (request.getParentId() == null) {
            return null;
        }
        return commentRepository.findById(request.getParentId())
                .orElseThrow(() -> new CommentNotFoundException());
    }

    private void checkNestedCommentAllowed(Comment parentComment) {
        if (parentComment != null && parentComment.getParent() != null) {
            throw new NestedCommentNotAllowedException();
        }
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


    private void doEmote(Emotion emotion, User user, Comment comment) {
        Optional<CommentEmotion> byCommentAndUser = commentEmotionRepository.findByCommentAndUser(comment, user);

        byCommentAndUser.ifPresentOrElse(
                commentEmotion -> {
                    if (emotion == commentEmotion.getEmotion()) {
                        //좋아요(싫어요)를 기존에 눌렀는데 또 눌렀을 경우 좋아요(싫어요) 취소
                        cancelEmotion(commentEmotion, comment);
                    } else {
                        //싫어요(좋아요)를 기존에 누른 상태로 좋아요(싫어요)를 누른 경우 싫어요(좋아요) 취소 후 좋아요(싫어요)로 등록
                        changeEmotion(commentEmotion, emotion, user, comment);
                    }
                },
                // 좋아요(싫어요)가 없을 경우 좋아요(싫어요) 추가
                () -> addEmotion(emotion, user, comment));
    }


    private void cancelEmotion(CommentEmotion commentEmotion, Comment comment) {
        commentEmotionRepository.delete(commentEmotion);
        comment.removeEmotion(commentEmotion);
        comment.updateLikeHateCount();
    }

    private void changeEmotion(CommentEmotion existingEmotion, Emotion newEmotion, User user, Comment comment) {
        commentEmotionRepository.delete(existingEmotion);
        comment.removeEmotion(existingEmotion);
        addEmotion(newEmotion, user, comment);
    }

    private void addEmotion(Emotion emotion, User user, Comment comment) {
        CommentEmotion newEmotion = new CommentEmotion();
        newEmotion.setEmote(emotion);
        newEmotion.mappingComment(comment);
        newEmotion.mappingUser(user);
        comment.updateLikeHateCount();
        commentEmotionRepository.save(newEmotion);
    }

    private List<RestaurantInfoDto> getRestaurantInfoList(String keyword, int page) {
        return (keyword != null)
                ? tourApiService.getRestaurantInfoByKeyWord(keyword, page, 1)
                : tourApiService.getRestaurantInfo(1, page);
    }

    private List<SearchRestaurantData> convertToSearchSnackResponseList(List<RestaurantInfoDto> restaurantInfo) {
        return restaurantInfo.stream()
                .map(restaurant -> new SearchRestaurantData(
                        restaurant.getContentId(),
                        restaurant.getTitle(),
                        restaurant.getFirstImage(),
                        tourApiService.getTreatMenu(restaurant.getContentId())
                ))
                .collect(Collectors.toList());
    }


}

