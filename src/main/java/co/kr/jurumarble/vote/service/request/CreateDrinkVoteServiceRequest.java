package co.kr.jurumarble.vote.service.request;

import co.kr.jurumarble.exception.vote.VoteTypeNotMatchException;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.domain.VoteDrinkContent;
import co.kr.jurumarble.vote.enums.VoteType;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;

@Getter
public class CreateDrinkVoteServiceRequest {
    private final String title;
    private final VoteType voteType;
    private final Long voteId;
    private final Long drinkAId;
    private final Long drinkBId;

    @Builder
    public CreateDrinkVoteServiceRequest(String title, VoteType voteType, Long voteId, Long drinkAId, Long drinkBId) {
        validVoteType(voteType);
        this.title = title;
        this.voteType = voteType;
        this.voteId = voteId;
        this.drinkAId = drinkAId;
        this.drinkBId = drinkBId;
    }

    private void validVoteType(VoteType voteType) {
        if (voteType != VoteType.DRINK) {
            throw new VoteTypeNotMatchException();
        }
    }

    public VoteDrinkContent toVoteDrinkContent() {
        return VoteDrinkContent.builder()
                .drinkAId(drinkAId)
                .drinkBId(drinkBId)
                .build();
    }

    public Vote toVote(Long userId) {
        return Vote.builder()
                .postedUserId(userId)
                .voteType(voteType)
                .title(title)
                .build();
    }

}
