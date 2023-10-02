package co.kr.jurumarble.vote.domain;

import co.kr.jurumarble.vote.dto.request.VoteWithPostedUserData;
import co.kr.jurumarble.vote.repository.dto.VoteWithPostedUserCommonData;

public interface VoteAction {
    VoteWithPostedUserData getVoteWithPostedUserData(Long voteId, VoteWithPostedUserCommonData voteCommonData);
}
