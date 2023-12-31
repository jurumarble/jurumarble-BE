package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.config.JpaAuditionConfig;
import co.kr.jurumarble.vote.repository.dto.HotDrinkVoteData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaAuditionConfig.class)
@TestPropertySource(locations = "classpath:application-dev.yml")
class VoteEntityRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;

    @Qualifier("voteEntityRepositoryImpl")
    @Autowired
    private VoteEntityRepository voteEntityRepository;

    @Autowired
    private EntityManager entityManager;

//    @DisplayName("키워드 없이 인기순으로 일반 투표를 조회한다.")
//    @Test
//    void findVoteDataWithPopularity() {
//        // given
//        PageRequest of = PageRequest.of(0, 10);
//
//        // when
//        Slice<VoteData> actual = voteEntityRepository.findVoteDataWithPopularity(null, of);
//
//        // then
//        assertThat(actual).hasSize(10)
//                .extracting("title", "votedCount", "titleA")
//                .containsExactly(
//                        tuple("테스트 투표 1", 10L, "A1"),
//                        tuple("테스트 투표 3", 3L, "E1"),
//                        tuple("테스트 투표 2", 2L, "C1"),
//                        tuple("테스트 투표 4", 2L, "G1"),
//                        tuple("테스트 투표 5", 1L, "I1"),
//                        tuple("테스트 투표 6", 1L, "K1"),
//                        tuple("테스트 투표 7", 1L, "M1"),
//                        tuple("테스트 투표 8", 0L, "O1"),
//                        tuple("테스트 투표 9", 0L, "Q1"),
//                        tuple("테스트 투표 10", 0L, "S1")
//                );
//    }
//
//    @DisplayName("인기순으로 일반 투표를 조회할때 키워드 검색을 한다.")
//    @Test
//    void findVoteDataWithPopularityAndKeyword() {
//        // given
//        PageRequest of = PageRequest.of(0, 10);
//
//        // when
//        Slice<VoteData> actual = voteEntityRepository.findVoteDataWithPopularity(null, of);
//
//        // then
//        assertThat(actual).hasSize(10)
//                .extracting("title", "votedCount", "titleA")
//                .containsExactly(
//                        tuple("테스트 투표 1", 10L, "A1"),
//                        tuple("테스트 투표 3", 3L, "E1"),
//                        tuple("테스트 투표 2", 2L, "C1"),
//                        tuple("테스트 투표 4", 2L, "G1"),
//                        tuple("테스트 투표 5", 1L, "I1"),
//                        tuple("테스트 투표 6", 1L, "K1"),
//                        tuple("테스트 투표 7", 1L, "M1"),
//                        tuple("테스트 투표 8", 0L, "O1"),
//                        tuple("테스트 투표 9", 0L, "Q1"),
//                        tuple("테스트 투표 10", 0L, "S1")
//                );
//    }


//    @DisplayName("일반 투표와 투표 컨텐츠를 같이 조회한다.")
//    @Test
//    void findVoteDataByVoteId() {
//        // given // when
//        VoteData voteData = voteEntityRepository.findVoteDataByVoteId(1L).orElseThrow(VoteNotFoundException::new);
//
//        // then
//        assertThat(Collections.singletonList(voteData)).extracting(
//                "title",
//                "detail",
//                "filteredAge",
//                "filteredGender",
//                "filteredMbti",
//                "imageA",
//                "titleA"
//        ).contains(
//                tuple("테스트 투표 1", "테스트 투표 상세 설명 1", AgeType.twenties, GenderType.FEMALE, MbtiType.INFP, "https://picsum.photos/200/300", "A1")
//        );
//
//
//    }

//    @DisplayName("키워드 없이 시간순으로 일반 투표를 조회한다.")
//    @Test
//    void findVoteDataWithTime() {
//        // given
//        PageRequest of = PageRequest.of(0, 7);
//
//        // when
//        Slice<VoteData> actual = voteEntityRepository.findVoteDataWithTime(null, of);
//
//        // then
//        assertThat(actual).hasSize(7)
//                .extracting("title", "titleA")
//                .containsExactly(
//                        tuple("테스트 투표 10", "S1"),
//                        tuple("테스트 투표 9", "Q1"),
//                        tuple("테스트 투표 8", "O1"),
//                        tuple("테스트 투표 7", "M1"),
//                        tuple("테스트 투표 6", "K1"),
//                        tuple("테스트 투표 5", "I1"),
//                        tuple("테스트 투표 4", "G1")
//                );
//    }

    @DisplayName("일주일 동안 투표 결과가 가장 많은 전통주 투표를 조회한다.")
    @Test
    void getHotDrinkVote() {
        // given
        LocalDateTime testTime = LocalDateTime.of(2023, 8, 4, 0, 0);

        // when
        HotDrinkVoteData hotDrinkVote = voteEntityRepository.getHotDrinkVote(testTime).get();

        // then
        assertThat(hotDrinkVote).extracting("voteTitle", "drinkAImage", "drinkBImage")
                .containsExactly("테스트 전통주 투표 1", "https://shopping-phinf.pstatic.net/main_8204301/82043013300.5.jpg", "https://shopping-phinf.pstatic.net/main_8475981/84759812651.1.jpg");

    }

}