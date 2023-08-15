package co.kr.jurumarble.drink.domain.dto;

import co.kr.jurumarble.exception.drink.DrinkIdDuplicatedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DrinkIdsUsedForVoteTest {

    @DisplayName("투표 아이디가 중복되면 DrinkIdDuplicatedException을 throw 한다.")
    @Test
    void test() {
        // given
        Long drinkAId = 1L;
        Long drinkBId = 1L;

        // when // then
        Assertions.assertThrows(DrinkIdDuplicatedException.class, () -> {
            new DrinkIdsUsedForVote(drinkAId, drinkBId);
        });

    }

}