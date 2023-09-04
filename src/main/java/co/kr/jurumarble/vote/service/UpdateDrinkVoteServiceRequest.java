package co.kr.jurumarble.vote.service;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateDrinkVoteServiceRequest {

    private final String title;
    private final Long drinkAId;
    private final Long drinkBId;

    @Builder
    public UpdateDrinkVoteServiceRequest(String title, Long drinkAId, Long drinkBId) {
        this.title = title;
        this.drinkAId = drinkAId;
        this.drinkBId = drinkBId;
    }
}
