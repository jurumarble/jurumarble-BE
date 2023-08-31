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
import java.util.List;

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

    @DisplayName("8월 3일부터 8월 10일까지 일주일간 즐겼어요가 많은 전통주를 조회한다.")
    @Test
    void getHotDrinks1() {
        // given
        PageRequest of = PageRequest.of(0, 3);
        LocalDateTime testTime = LocalDateTime.of(2023, 8, 10, 7, 15);

        // when
        List<HotDrinkData> hotDrinks = drinkEntityRepository.getHotDrinks(of, testTime);

        // then
        assertThat(hotDrinks).hasSize(3)
                .extracting("drinkId", "name", "enjoyedCount")
                .containsExactly(
                        tuple(12L, "화주", 3L),
                        tuple(15L, "나루 생막걸리 6%", 2L),
                        tuple(30L, "붉은 원숭이", 2L)
                );
    }

    @DisplayName("7월 27일부터 8월 3일까지 일주일간 즐겼어요가 많은 전통주를 조회한다.")
    @Test
    void getHotDrinks2() {
        // given
        PageRequest of = PageRequest.of(0, 3);
        LocalDateTime testTime = LocalDateTime.of(2023, 8, 3, 7, 15);

        // when
        List<HotDrinkData> hotDrinks = drinkEntityRepository.getHotDrinks(of, testTime);

        // then
        assertThat(hotDrinks).hasSize(3)
                .extracting("drinkId", "name", "enjoyedCount")
                .containsExactly(
                        tuple(11L, "문경바람 오크 40도", 2L),
                        tuple(20L, "은자골 생 탁배기", 2L),
                        tuple(3L, "제주 고소리술", 2L)
                );
    }

    @DisplayName("9월 20일부터 9월 27일까지 일주일간 즐겼어요가 많은 전통주를 조회한다.")
    @Test
    void getHotDrinks3() {
        // given
        PageRequest of = PageRequest.of(0, 3);
        LocalDateTime testTime = LocalDateTime.of(2023, 9, 20, 7, 15);

        // when
        List<HotDrinkData> hotDrinks = drinkEntityRepository.getHotDrinks(of, testTime);

        // then
        assertThat(hotDrinks).hasSize(0);
    }

    @DisplayName("전통주를 즐겼어요가 많은 순서로 조회한다.")
    @Test
    void findDrinksByPopular(){
        // given
        PageRequest of = PageRequest.of(0, 5);
        // when
        List<HotDrinkData> actual = drinkEntityRepository.findDrinksByPopular(of);

        // then
        assertThat(actual).hasSize(5)
                .extracting("drinkId", "name", "enjoyedCount")
                .containsExactly(
                        tuple(2L, "송명섭 막걸리", 3L),
                        tuple(3L, "제주 고소리술", 3L),
                        tuple(12L, "화주", 3L),
                        tuple(8L, "김포예주 프리미엄", 2L),
                        tuple(15L, "나루 생막걸리 6%", 2L)
                );

    }
}
