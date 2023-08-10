package co.kr.jurumarble.vote.domain;

import co.kr.jurumarble.exception.vote.VoteDrinksDuplicatedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VoteDrinkContentTest {

    @DisplayName("전통주 컨텐츠로 중복된 전통주를 입력하면 VoteDrinksDuplicatedException를 throw한다.")
    @Test
    void validateDrinksDuplicated(){
        // given // when
        Long duplicatedDrinkId = 2L;

        // then
        Assertions.assertThrows(VoteDrinksDuplicatedException.class, () -> {
            VoteDrinkContent.builder()
                    .voteId(1L)
                    .drinkIdA(duplicatedDrinkId)
                    .drinkIdB(duplicatedDrinkId)
                    .build();
        });
    }

}