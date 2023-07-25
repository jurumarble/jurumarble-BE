package co.kr.jurumarble.vote.service;

import co.kr.jurumarble.exception.user.UserNotAccessRightException;
import co.kr.jurumarble.exception.user.UserNotFoundException;
import co.kr.jurumarble.exception.vote.VoteNotFoundException;
import co.kr.jurumarble.user.domain.UserEntity;
import co.kr.jurumarble.user.repository.UserRepository;
import co.kr.jurumarble.vote.domain.VoteContentEntity;
import co.kr.jurumarble.vote.domain.VoteEntity;
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

        UserEntity findUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        VoteContentEntity voteContent = new VoteContentEntity(request);
        VoteEntity vote = new VoteEntity(request, findUser, voteContent);

        voteRepository.save(vote);
    }

    public GetVoteResponse getVote(Long voteId) {
        return voteRepository.findById(voteId)
                .map(VoteEntity::toDto)
                .orElseThrow(VoteNotFoundException::new);
    }

    public void updateVote(UpdateVoteRequest request, Long userId, Long voteId) {

        VoteEntity vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        isUserVote(userId, vote);

        vote.update(request);

    }

    public void isUserVote(Long userId, VoteEntity vote) {

        if(!vote.isUsersVote(userId)) {
            throw new UserNotAccessRightException();
        }
    }

    public void deleteVote(Long voteId, Long userId) {

        VoteEntity vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        isUserVote(userId, vote);

        voteRepository.delete(vote);
    }
}
