package co.kr.jurumarble.comment.service;

import co.kr.jurumarble.comment.domain.Comment;
import co.kr.jurumarble.comment.enums.CommentType;
import co.kr.jurumarble.comment.repository.CommentRepository;
import co.kr.jurumarble.comment.service.request.CreateCommentServiceRequest;
import co.kr.jurumarble.drink.domain.entity.Drink;
import co.kr.jurumarble.drink.repository.DrinkRepository;
import co.kr.jurumarble.exception.comment.*;
import co.kr.jurumarble.exception.drink.DrinkNotFoundException;
import co.kr.jurumarble.exception.vote.VoteNotFoundException;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentValidator {

    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;
    private final DrinkRepository drinkRepository;

    public void validateCommentBelongsToType(CommentType commentType, Long typeId, Comment commment) {
        switch (commentType) {
            case VOTES:
                Vote vote = voteRepository.findById(typeId).orElseThrow(VoteNotFoundException::new);
                validateCommentBelongsToVote(commment, vote);
                break;
            case DRINKS:
                Drink drink = drinkRepository.findById(typeId).orElseThrow(DrinkNotFoundException::new);
                validateCommentBelongsToDrink(commment, drink);
                break;
            default:
                throw new InvalidCommentTypeException();
        }
    }

    public void validateCommentBelongsToUser(Comment comment, User user) {
        if (!commentRepository.existsByIdAndUser(comment.getId(), user)) {
            throw new CommentNotBelongToUserException();
        }
    }

    public void validateCommentBelongsToVote(Comment parent, Vote vote) {
        if (parent != null && !commentRepository.existsByIdAndVoteId(parent.getId(), vote.getId())) {
            throw new CommentNotBelongToVoteException();
        }
    }

    public void validateCommentBelongsToDrink(Comment parent, Drink drink) {
        if (parent != null && !commentRepository.existsByIdAndDrinkId(parent.getId(), drink.getId())) {
            throw new CommentNotBelongToDrinkException();
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
