package co.kr.jurumarble.drink.repository;

import co.kr.jurumarble.config.JpaAuditionConfig;
import co.kr.jurumarble.drink.repository.dto.HotDrinkData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@DataJpaTest
@Import(JpaAuditionConfig.class)
@TestPropertySource(locations = "classpath:application-dev.yml")
class DrinkEntityRepositoryImplTest {

    @Autowired
    private DrinkRepository drinkRepository;

    @Qualifier("drinkEntityRepositoryImpl")
    @Autowired
    private DrinkEntityRepository drinkEntityRepository;

    @Autowired
    private EntityManager entityManager;

    @DisplayName("")
    @Test
    void test() {
        // given
        PageRequest of = PageRequest.of(0, 3);
        LocalDateTime testTime = LocalDateTime.of(2023, 8, 10, 7, 15);

        // when
        Slice<HotDrinkData> hotDrinks = drinkEntityRepository.getHotDrinks(of, testTime);

        // then
        assertThat(hotDrinks).hasSize(3)
                .extracting("drinkId", "name", "enjoyedCount")
                .containsExactly(
                        tuple(12L, "느린마을 막걸리", 3L),
                        tuple(2L, "인천 생 소성주", 2L),
                        tuple(30L, "호랑이 생 막걸리", 2L)
                );
    }
}
