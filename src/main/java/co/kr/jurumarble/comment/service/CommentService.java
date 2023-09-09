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
import co.kr.jurumarble.exception.comment.CommentNotFoundException;
import co.kr.jurumarble.exception.user.UserNotFoundException;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentEmoteManager commentEmoteManager;
    private final TourApiDataManager tourApiDataManager;
    private final CommentValidator commentValidator;
    private final CommentFinder commentFinder;
    private final CommentVoteService commentVoteService;
    private final CommentConverter commentConverter;

    @Transactional
    public void createComment(CommentType commentType, Long typeId, Long userId, CreateCommentServiceRequest request) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Comment parentComment = commentValidator.checkParentComment(request);
        commentValidator.checkNestedCommentAllowed(parentComment);
        commentValidator.validateCommentBelongsToType(commentType, typeId, parentComment);
        commentValidator.validateUserVotedBeforeCommenting(commentType, typeId, userId);
        Long drinkId = commentVoteService.getDrinkIdIfApplicable(commentType, typeId, userId).orElse(null);
        Comment comment = request.toComment(commentType, parentComment, user, typeId, drinkId);
        commentRepository.save(comment);

    }


    public Page<GetCommentData> getComments(CommentType commentType, Long typeId, GetCommentServiceRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        List<Comment> comments = commentFinder.findCommentsBySortType(commentType, typeId, request, pageable);
        commentFinder.findChildCommentByParentComment(comments);
        List<GetCommentData> getCommentData = commentConverter.convertToCommentDataList(comments, commentType, typeId);
        int totalComments = commentFinder.findTotalCommentsCount(commentType, typeId);
        return new PageImpl<>(getCommentData, pageable, totalComments);
    }

    public List<GetCommentData> getSampleComments(CommentType commentType, Long typeId) {
        List<Comment> comments = commentFinder.findSampleComments(commentType, typeId);
        List<GetCommentData> getCommentData = commentConverter.convertToCommentDataList(comments, commentType, typeId);
        return getCommentData;
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

}

