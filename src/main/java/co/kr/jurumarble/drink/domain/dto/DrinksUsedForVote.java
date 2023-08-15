package co.kr.jurumarble.drink.domain.dto;

import co.kr.jurumarble.drink.domain.Drink;
import co.kr.jurumarble.exception.drink.DrinkIdDuplicatedException;
import lombok.Getter;

@Getter
public class DrinksUsedForVote {
    private final Drink drinkA;
    private final Drink drinkB;

    public DrinksUsedForVote(Drink drinkA, Drink drinkB) {
        validDrinkIsDuplicated(drinkA, drinkB);
        this.drinkA = drinkA;
        this.drinkB = drinkB;
    }

    private void validDrinkIsDuplicated(Drink drinkA, Drink drinkB) {
        if (drinkA.equals(drinkB)) {
            throw new DrinkIdDuplicatedException();
        }
    }
}
