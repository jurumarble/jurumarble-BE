package co.kr.jurumarble.vote.service.request;

import co.kr.jurumarble.drink.domain.dto.DrinkIdsUsedForVote;
import co.kr.jurumarble.exception.vote.VoteTypeNotMatchException;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.enums.VoteType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateDrinkVoteServiceRequest {
    private final String title;
    private final VoteType voteType;
    private final Long voteId;
    private final Long drinkAId;
    private final Long drinkBId;
    private final String detail;

    @Builder
    public CreateDrinkVoteServiceRequest(String title, VoteType voteType, Long voteId, Long drinkAId, Long drinkBId, String detail) {
        validVoteType(voteType);
        this.title = title;
        this.voteType = voteType;
        this.voteId = voteId;
        this.drinkAId = drinkAId;
        this.drinkBId = drinkBId;
        this.detail = detail;
    }

    private void validVoteType(VoteType voteType) {
        if (voteType != VoteType.DRINK) {
            throw new VoteTypeNotMatchException();
        }
    }

    public DrinkIdsUsedForVote extractDrinkIds() {
        return new DrinkIdsUsedForVote(drinkAId, drinkBId);
    }

    public Vote toVote(Long userId) {
        return Vote.builder()
                .postedUserId(userId)
                .voteType(voteType)
                .title(title)
                .detail(detail)
                .build();
    }

}
