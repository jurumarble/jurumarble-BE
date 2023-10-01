package co.kr.jurumarble.vote.domain;

import co.kr.jurumarble.exception.vote.VoteDrinkContentNotFoundException;
import co.kr.jurumarble.vote.dto.request.VoteWithPostedUserData;
import co.kr.jurumarble.vote.repository.VoteDrinkContentRepository;
import co.kr.jurumarble.vote.repository.dto.VoteWithPostedUserCommonData;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DrinkVoteAction implements VoteAction{

    private final VoteDrinkContentRepository voteDrinkContentRepository;
    @Override
    public VoteWithPostedUserData getVoteWithPostedUserData(Long voteId, VoteWithPostedUserCommonData voteCommonData) {
        VoteDrinkContent voteDrinkContent = voteDrinkContentRepository.findByVoteId(voteId).orElseThrow(VoteDrinkContentNotFoundException::new);
        return VoteWithPostedUserData.generateDrinkVoteData(voteCommonData, voteDrinkContent);
    }
}
