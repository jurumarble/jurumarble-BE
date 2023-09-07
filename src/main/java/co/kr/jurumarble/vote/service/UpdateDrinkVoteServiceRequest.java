package co.kr.jurumarble.vote.service;

import co.kr.jurumarble.drink.domain.dto.DrinkIdsUsedForVote;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateDrinkVoteServiceRequest {

    private final Long voteId;
    private final Long userId;
    private final String title;
    private final String detail;
    private final Long drinkAId;
    private final Long drinkBId;

    @Builder
    public UpdateDrinkVoteServiceRequest(Long voteId, Long userId, String title, String detail, Long drinkAId, Long drinkBId) {
        this.voteId = voteId;
        this.userId = userId;
        this.title = title;
        this.detail = detail;
        this.drinkAId = drinkAId;
        this.drinkBId = drinkBId;
    }

    public DrinkIdsUsedForVote extractDrinkIds() {
        return new DrinkIdsUsedForVote(drinkAId, drinkBId);
    }
}
