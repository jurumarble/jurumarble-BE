package co.kr.jurumarble.vote.domain;

import co.kr.jurumarble.exception.user.UserNotAccessRightException;
import co.kr.jurumarble.exception.vote.AlreadyUserDoVoteException;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.vote.repository.VoteResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoteValidator {

    private final VoteResultRepository voteResultRepository;

    public void validPostedUserWhenParcitipateVote(Vote vote, User user) {
        if (vote.isVoteOfUser(user.getId())) throw new UserNotAccessRightException();
    }

    public void validAlreadyParcitipatedVote(Vote vote, User user) {
        if (voteResultRepository.existsByVoteIdAndVotedUserId(vote.getId(), user.getId()))
            throw new AlreadyUserDoVoteException();
    }
}
