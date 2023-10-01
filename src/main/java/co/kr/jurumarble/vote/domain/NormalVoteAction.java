package co.kr.jurumarble.vote.domain;

import co.kr.jurumarble.exception.vote.VoteContentNotFoundException;
import co.kr.jurumarble.vote.dto.request.VoteWithPostedUserData;
import co.kr.jurumarble.vote.repository.VoteContentRepository;
import co.kr.jurumarble.vote.repository.dto.VoteWithPostedUserCommonData;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NormalVoteAction implements VoteAction{
    private final VoteContentRepository voteContentRepository;
    @Override
    public VoteWithPostedUserData getVoteWithPostedUserData( Long voteId, VoteWithPostedUserCommonData voteCommonData) {
        VoteContent voteContent = voteContentRepository.findByVoteId(voteId).orElseThrow(VoteContentNotFoundException::new);
        return VoteWithPostedUserData.generateNormalVoteData(voteCommonData, voteContent);
    }
}
