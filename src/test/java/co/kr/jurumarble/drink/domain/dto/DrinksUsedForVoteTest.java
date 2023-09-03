package co.kr.jurumarble.drink.domain.dto;

import co.kr.jurumarble.drink.domain.entity.Drink;
import co.kr.jurumarble.exception.drink.DrinkIdDuplicatedException;
import co.kr.jurumarble.exception.vote.DrinkVoteHasOtherRegionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DrinksUsedForVoteTest {

    @DisplayName("전통주 투표에 들어가는 전통주가 같으면 DrinkIdDuplicatedException를 throw 한다.")
    @Test
    void validDrinkIsDuplicated() {
        // given // when
        Long drinkId = 1L;

        Drink drinkA = Drink.builder()
                .id(drinkId)
                .region("서울")
                .build();

        Drink drinkB = Drink.builder()
                .id(drinkId)
                .region("서울")
                .build();

        // then
        Assertions.assertThrows(DrinkIdDuplicatedException.class, () -> {
            new DrinksUsedForVote(drinkA, drinkB);
        });
    }

    @DisplayName("전통주 투표에 들어가는 전통주의 지역이 다르면 DrinkVoteHasOtherRegionException을 throw 한다.")
    @Test
    void validateDrinksForVoteHaveSameRegion() {
        // given // when

        Drink drinkA = Drink.builder()
                .id(1L)
                .region("서울")
                .build();

        Drink drinkB = Drink.builder()
                .id(2L)
                .region("부산")
                .build();
        // then
        Assertions.assertThrows(DrinkVoteHasOtherRegionException.class, () -> {
            new DrinksUsedForVote(drinkA, drinkB);
        });
    }

}