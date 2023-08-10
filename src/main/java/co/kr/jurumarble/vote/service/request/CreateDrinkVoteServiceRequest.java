package co.kr.jurumarble.vote.service.request;

import co.kr.jurumarble.exception.vote.VoteTypeNotMatchException;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.domain.VoteDrinkContent;
import co.kr.jurumarble.vote.enums.VoteType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateDrinkVoteServiceRequest {
    private String title;
    private Long drinkIdA;
    private Long drinkIdB;
    private VoteType voteType;

    @Builder
    public CreateDrinkVoteServiceRequest(String title, Long drinkIdA, Long drinkIdB, VoteType voteType) {
        validVoteType(voteType);
        this.title = title;
        this.drinkIdA = drinkIdA;
        this.drinkIdB = drinkIdB;
        this.voteType = voteType;
    }

    private void validVoteType(VoteType voteType) {
        if (voteType != VoteType.DRINK) {
            throw new VoteTypeNotMatchException();
        }
    }

    public VoteDrinkContent toVoteDrinkContent() {
        return VoteDrinkContent.builder()
                .drinkIdA(drinkIdA)
                .drinkIdB(drinkIdB)
                .build();
    }

    public Vote toVote(Long userId) {
        return Vote.builder()
                .postedUserId(userId)
                .title(title)
                .build();
    }

}
