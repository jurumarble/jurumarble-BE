package co.kr.jurumarble.comment.service;

import co.kr.jurumarble.comment.domain.Comment;
import co.kr.jurumarble.comment.enums.CommentType;
import co.kr.jurumarble.exception.vote.VoteResultNotFoundException;
import co.kr.jurumarble.user.enums.ChoiceType;
import co.kr.jurumarble.vote.domain.VoteResult;
import co.kr.jurumarble.vote.enums.VoteType;
import co.kr.jurumarble.vote.repository.VoteDrinkContentRepository;
import co.kr.jurumarble.vote.repository.VoteRepository;
import co.kr.jurumarble.vote.repository.VoteResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentVoteService {
    private final VoteRepository voteRepository;
    private final VoteResultRepository voteResultRepository;
    private final VoteDrinkContentRepository voteDrinkContentRepository;

    public Optional<Long> getDrinkIdIfApplicable(CommentType commentType, Long typeId, Long userId) {
        if (commentType != CommentType.VOTES) {
            return Optional.empty();
        }

        return voteRepository.findById(typeId)
                .filter(vote -> vote.getVoteType() == VoteType.DRINK)
                .flatMap(vote -> voteResultRepository.findByVotedUserIdAndVoteId(userId, typeId))
                .flatMap(voteResult -> voteDrinkContentRepository.findByVoteId(typeId)
                        .map(choice -> (voteResult.getChoice() == ChoiceType.A) ? choice.getDrinkAId() : choice.getDrinkBId()));
    }

    public ChoiceType getChoiceType(Comment comment, CommentType commentType, Long typeId) {
        if (commentType == CommentType.VOTES) {
            VoteResult voteResult = voteResultRepository.findByVotedUserIdAndVoteId(comment.getUser().getId(), typeId)
                    .orElseThrow(VoteResultNotFoundException::new);
            return voteResult.getChoice();
        }
        return null;
    }
}
