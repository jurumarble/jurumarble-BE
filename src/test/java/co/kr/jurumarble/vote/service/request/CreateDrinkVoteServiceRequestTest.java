package co.kr.jurumarble.vote.service.request;

import co.kr.jurumarble.exception.vote.VoteTypeNotMatchException;
import co.kr.jurumarble.vote.enums.VoteType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateDrinkVoteServiceRequestTest {


    @DisplayName("투표 타입으로 전통주 투표가 아닌 값을 넣으면 예외를 터트린다.")
    @Test
    void test() {
        // given // when // then
        Assertions.assertThrows(VoteTypeNotMatchException.class, () -> {
            CreateDrinkVoteServiceRequest.builder()
                    .drinkIdA(1L)
                    .drinkIdB(2L)
                    .voteType(VoteType.NORMAL)
                    .build();

        });
    }

}