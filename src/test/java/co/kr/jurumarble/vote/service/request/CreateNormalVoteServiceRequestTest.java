package co.kr.jurumarble.vote.service.request;

import co.kr.jurumarble.exception.vote.VoteTypeNotMatchException;
import co.kr.jurumarble.vote.enums.VoteType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateNormalVoteServiceRequestTest {

    @DisplayName("투표 타입으로 일반 투표가 아닌 값을 넣으면 예외를 터트린다.")
    @Test
    void test() {
        // given // when // then
        Assertions.assertThrows(VoteTypeNotMatchException.class, () -> {
            CreateNormalVoteServiceRequest.builder()
                    .title("title A")
                    .titleB("title B")
                    .imageA("image A")
                    .imageB("image B")
                    .voteType(VoteType.DRINK)
                    .build();

        });
    }

}