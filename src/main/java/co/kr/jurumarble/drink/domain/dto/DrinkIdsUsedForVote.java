package co.kr.jurumarble.drink.domain.dto;

import co.kr.jurumarble.exception.drink.DrinkIdDuplicatedException;
import lombok.Getter;

import java.util.List;

@Getter
public class DrinkIdsUsedForVote {

    private Long drinkAId;

    private Long drinkBId;

    public DrinkIdsUsedForVote(Long drinkAId, Long drinkBId) {
        validateDrinkIdsIsDuplicate(drinkAId, drinkBId);
        this.drinkAId = drinkAId;
        this.drinkBId = drinkBId;
    }

    private void validateDrinkIdsIsDuplicate(Long drinkAId, Long drinkBId) {
        if (drinkAId.equals(drinkBId)) {
            throw new DrinkIdDuplicatedException();
        }
    }

    public List<Long> toDrinkIds() {
        return List.of(drinkAId, drinkBId);
    }
}
