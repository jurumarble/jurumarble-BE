package co.kr.jurumarble.vote.service;

import co.kr.jurumarble.exception.user.UserNotAccessRightException;
import co.kr.jurumarble.exception.user.UserNotFoundException;
import co.kr.jurumarble.exception.vote.VoteNotFoundException;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.repository.UserRepository;
import co.kr.jurumarble.vote.domain.VoteContent;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.dto.request.CreateVoteRequest;
import co.kr.jurumarble.vote.dto.request.UpdateVoteRequest;
import co.kr.jurumarble.vote.dto.response.GetVoteResponse;
import co.kr.jurumarble.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class VoteService {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    public void createVote(CreateVoteRequest request, Long userId) {

        User findUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        VoteContent voteContent = new VoteContent(request);
        Vote vote = new Vote(request, findUser, voteContent);

        voteRepository.save(vote);
    }

    public GetVoteResponse getVote(Long voteId) {
        return voteRepository.findById(voteId)
                .map(Vote::toDto)
                .orElseThrow(VoteNotFoundException::new);
    }

    public void updateVote(UpdateVoteRequest request, Long userId, Long voteId) {

        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        isUserVote(userId, vote);

        vote.update(request);

    }

    public void isUserVote(Long userId, Vote vote) {

        if(!vote.isUsersVote(userId)) {
            throw new UserNotAccessRightException();
        }
    }

    public void deleteVote(Long voteId, Long userId) {

        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        isUserVote(userId, vote);

        voteRepository.delete(vote);
    }
}
