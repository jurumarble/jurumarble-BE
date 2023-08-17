package co.kr.jurumarble.drink.domain;

import co.kr.jurumarble.config.JpaAuditionConfig;
import co.kr.jurumarble.drink.domain.dto.DrinkIdsUsedForVote;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(JpaAuditionConfig.class)
class DrinkFinderTest {

    @DisplayName("전통주 투표에 사용할 투표들을 아이디 리스트로 조회한다.")
    @Test
    void findDrinksUsedForVote() {
        // given
        DrinkIdsUsedForVote drinkIdsUsedForVote = new DrinkIdsUsedForVote(1L, 2L);

        // when

        // then

    }


}