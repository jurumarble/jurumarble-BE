package co.kr.jurumarble.comment.service;

import co.kr.jurumarble.comment.domain.Comment;
import co.kr.jurumarble.comment.dto.request.CommentCreateRequest;
import co.kr.jurumarble.comment.dto.request.CommentGetRequest;
import co.kr.jurumarble.comment.dto.request.CommentUpdateRequest;
import co.kr.jurumarble.comment.repository.CommentRepository;
import co.kr.jurumarble.exception.comment.CommentNotFoundException;
import co.kr.jurumarble.exception.user.UserNotFoundException;
import co.kr.jurumarble.exception.vote.VoteNotFoundException;
import co.kr.jurumarble.user.repository.UserRepository;
import co.kr.jurumarble.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;
    private final CommentRepository commentRepository;

    public void createComment(Long voteId, Long userId, CommentCreateRequest request) {
        validateUserAndVote(voteId, userId);
        Comment parentComment = checkParentComment(request);

        Comment comment = new Comment(request, parentComment);
        commentRepository.save(comment);

    }

    public Slice<Comment> getCommentsBySortType(Long voteId, CommentGetRequest request) {

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        getCommentBySortType(voteId, request, pageable);


    }

    private void getCommentBySortType(Long voteId, CommentGetRequest request, Pageable pageable) {
        List<Comment> comments; //댓글
        List<Comment> childComments = new ArrayList<>(); //대댓글

        List<Comment> comments = getCommentsBySortType(voteId, request, pageable);

        int countComments = commentRepository.countCommentsByVoteId(voteId);

        //대댓글 가져오기
        for (Comment parentComment : comments) {
            childComments.addAll(commentRepository.findByParentComment(parentComment));
        }

        comments.addAll(childComments);
    }

    private List<Comment> getCommentsBySortType(Long voteId, CommentGetRequest request, Pageable pageable) {
        List<Comment> comments;
        switch (request.getSortBy()) {
            case ByTime:
                comments = commentRepository.findNewestComments(voteId, pageable);
                break;
            case ByPopularity:
                comments = commentRepository.findHotComments(voteId, pageable);
                break;
            default:
                throw new IllegalArgumentException("적절한 정렬 방식이 아닙니다.");   //예외처리 분리해야 함
        }
        return comments;
    }


    public void updateComment(Long voteId, Long commentId, Long userId, CommentUpdateRequest request) {
        validateUserAndVote(voteId, userId);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        comment.updateContent(request);
    }

    public void deleteComment(Long voteId, Long commentId, Long userId) {
        validateUserAndVote(voteId, userId);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        commentRepository.delete(comment);
    }

    private void validateUserAndVote(Long voteId, Long userId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);
    }

    private Comment checkParentComment(CommentCreateRequest request) {
        Comment parentComment = null != request.getParentId()
                ? commentRepository.findById(request.getParentId())
                .orElseThrow(() -> new CommentNotFoundException())
                : null;
        return parentComment;
    }

}
