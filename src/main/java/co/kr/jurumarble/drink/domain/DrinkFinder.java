package co.kr.jurumarble.drink.domain;

import co.kr.jurumarble.drink.domain.dto.DrinkIdsUsedForVote;
import co.kr.jurumarble.drink.domain.dto.DrinksUsedForVote;
import co.kr.jurumarble.drink.domain.entity.Drink;
import co.kr.jurumarble.drink.repository.DrinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DrinkFinder {

    private static final int DRINK_FIRST_INDEX_USED_FOR_VOTE = 0;
    private static final int DRINK_SECOND_INDEX_USED_FOR_VOTE = 1;

    private final DrinkRepository drinkRepository;

    public DrinksUsedForVote findDrinksUsedForVote(DrinkIdsUsedForVote drinkIdsUsedForVote) {
        List<Drink> drinksByIdIn = drinkRepository.findDrinksByIdIn(drinkIdsUsedForVote.toDrinkIds());
        return new DrinksUsedForVote(
                drinksByIdIn.get(DRINK_FIRST_INDEX_USED_FOR_VOTE),
                drinksByIdIn.get(DRINK_SECOND_INDEX_USED_FOR_VOTE)
        );
    }
}
