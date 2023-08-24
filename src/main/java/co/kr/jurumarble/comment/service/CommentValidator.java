package co.kr.jurumarble.comment.service;

import co.kr.jurumarble.comment.domain.Comment;
import co.kr.jurumarble.comment.repository.CommentRepository;
import co.kr.jurumarble.comment.service.request.CreateCommentServiceRequest;
import co.kr.jurumarble.exception.comment.CommentNotBelongToUserException;
import co.kr.jurumarble.exception.comment.CommentNotFoundException;
import co.kr.jurumarble.exception.comment.NestedCommentNotAllowedException;
import co.kr.jurumarble.exception.comment.ParentCommentNotBelongToVoteException;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.vote.domain.Vote;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentValidator {

    private final CommentRepository commentRepository;

    public void validateCommentBelongsToUser(Comment comment, User user) {
        if (!commentRepository.existsByIdAndUser(comment.getId(), user)) {
            throw new CommentNotBelongToUserException();
        }
    }

    public void validateParentCommentBelongsToVote(Comment parent, Vote vote) {
        if (parent != null && !commentRepository.existsByIdAndVoteId(parent.getId(), vote.getId())) {
            throw new ParentCommentNotBelongToVoteException();
        }
    }


    public Comment checkParentComment(CreateCommentServiceRequest request) {
        if (request.getParentId() == null) {
            return null;
        }
        return commentRepository.findById(request.getParentId())
                .orElseThrow(() -> new CommentNotFoundException());
    }

    public void checkNestedCommentAllowed(Comment parentComment) {
        if (parentComment != null && parentComment.getParent() != null) {
            throw new NestedCommentNotAllowedException();
        }
    }



}
