package co.kr.jurumarble.drink.domain.dto;

import co.kr.jurumarble.drink.domain.entity.Drink;
import co.kr.jurumarble.exception.drink.DrinkIdDuplicatedException;
import co.kr.jurumarble.exception.vote.DrinkVoteHasOtherRegionException;
import lombok.Getter;

import java.util.List;

@Getter
public class DrinksUsedForVote {
    private final Drink drinkA;
    private final Drink drinkB;

    public DrinksUsedForVote(Drink drinkA, Drink drinkB) {
        validDrinkIsDuplicated(drinkA, drinkB);
        validateDrinksForVoteHaveSameRegion(drinkA, drinkB);
        this.drinkA = drinkA;
        this.drinkB = drinkB;
    }

    private void validDrinkIsDuplicated(Drink drinkA, Drink drinkB) {
        if (drinkA.equals(drinkB)) {
            throw new DrinkIdDuplicatedException();
        }
    }

    private void validateDrinksForVoteHaveSameRegion(Drink drinkA, Drink drinkB) {
        if (!drinkA.hasSameRegion(drinkB)) {
            throw new DrinkVoteHasOtherRegionException();
        }
    }
}
