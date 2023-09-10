package co.kr.jurumarble.comment.service;

import co.kr.jurumarble.comment.domain.Comment;
import co.kr.jurumarble.comment.enums.CommentType;
import co.kr.jurumarble.comment.repository.CommentRepository;
import co.kr.jurumarble.comment.service.request.GetCommentServiceRequest;
import co.kr.jurumarble.exception.comment.InvalidCommentTypeException;
import co.kr.jurumarble.exception.comment.InvalidSortingMethodException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentFinder {

    private final CommentRepository commentRepository;

    public List<Comment> findCommentsBySortType(CommentType commentType, Long typeId, GetCommentServiceRequest request, Pageable pageable) {
        switch (request.getSortBy()) {
            case ByTime:
                return findNewestComments(commentType, typeId, pageable);
            case ByPopularity:
                return findHotComments(commentType, typeId, pageable);
            default:
                throw new InvalidSortingMethodException();
        }
    }

    public List<Comment> findSampleComments(CommentType commentType, Long typeId) {
        List<Comment> topCommentList = findHotComments(commentType, typeId, PageRequest.of(0, 1));

        // Hot comment가 없는 경우 바로 빈 리스트 반환
        if (topCommentList.isEmpty()) {
            return Collections.emptyList();
        }

        Comment topComment = topCommentList.get(0);
        List<Comment> newestComments = findNewestComments(commentType, typeId, PageRequest.of(0, 3));

        List<Comment> sampleComments = new ArrayList<>();
        sampleComments.add(topComment);

        for (Comment comment : newestComments) {
            if (!comment.equals(topComment)) {
                sampleComments.add(comment);
            }
            if (sampleComments.size() == 3) { // 최대 3개까지만 추가
                break;
            }
        }

        return sampleComments;
    }


    public void findChildCommentByParentComment(List<Comment> comments) {
        List<Comment> childComments = new ArrayList<>(); //대댓글
        //대댓글 가져오기
        for (Comment parent : comments) {
            childComments.addAll(commentRepository.findByParent(parent));
        }
        comments.addAll(childComments);
    }

    public int findTotalCommentsCount(CommentType commentType, Long typeId) {
        switch (commentType) {
            case VOTES:
                return commentRepository.countByVoteId(typeId);
            case DRINKS:
                return commentRepository.countByDrinkId(typeId);
            default:
                throw new InvalidCommentTypeException();
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
}
