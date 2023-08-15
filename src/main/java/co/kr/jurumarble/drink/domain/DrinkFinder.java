package co.kr.jurumarble.drink.domain;

import co.kr.jurumarble.drink.domain.dto.DrinkIdsUsedForVote;
import co.kr.jurumarble.drink.repository.DrinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DrinkFinder {

    private final DrinkRepository drinkRepository;

    public List<Drink> findDrinksUsedForVote(DrinkIdsUsedForVote drinkIdsUsedForVote) {
        return drinkRepository.findDrinksByIdIn(drinkIdsUsedForVote.toDrinkIds());
    }
}
