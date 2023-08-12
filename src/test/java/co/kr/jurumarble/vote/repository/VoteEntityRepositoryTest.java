package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.config.JpaAuditionConfig;
import co.kr.jurumarble.exception.vote.VoteNotFoundException;
import co.kr.jurumarble.user.enums.AgeType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import co.kr.jurumarble.vote.dto.NormalVoteData;
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
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

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

    @DisplayName("인기순으로 일반 투표를 조회한다.")
    @Test
    void findVoteDataWithPopularity() {
        // given
        PageRequest of = PageRequest.of(0, 10);

        // when
        Slice<NormalVoteData> actual = voteEntityRepository.findNormalVoteDataWithPopularity(null,of);

        // then
        assertThat(actual).hasSize(10)
                .extracting("title", "votedCount", "titleA")
                .containsExactly(
                        tuple("테스트 투표 1", 10L, "A1"),
                        tuple("테스트 투표 3", 3L, "E1"),
                        tuple("테스트 투표 2", 2L, "C1"),
                        tuple("테스트 투표 4", 2L, "G1"),
                        tuple("테스트 투표 5", 1L, "I1"),
                        tuple("테스트 투표 6", 1L, "K1"),
                        tuple("테스트 투표 7", 1L, "M1"),
                        tuple("테스트 투표 8", 0L, "O1"),
                        tuple("테스트 투표 9", 0L, "Q1"),
                        tuple("테스트 투표 10", 0L, "S1")
                );
    }


    @DisplayName("일반 투표와 투표 컨텐츠를 같이 조회한다.")
    @Test
    void findVoteDataByVoteId() {
        // given // when
        NormalVoteData normalVoteData = voteEntityRepository.findNormalVoteDataByVoteId(1L).orElseThrow(VoteNotFoundException::new);

        // then
        assertThat(Collections.singletonList(normalVoteData)).extracting(
                "title",
                "detail",
                "filteredAge",
                "filteredGender",
                "filteredMbti",
                "imageA",
                "titleA"
        ).contains(
                tuple("테스트 투표 1", "테스트 투표 상세 설명 1", AgeType.twenties, GenderType.FEMALE, MbtiType.INFP, "https://picsum.photos/200/300", "A1")
        );


    }

    @DisplayName("시간순으로 일반 투표를 조회한다.")
    @Test
    void findVoteDataWithTime() {
        // given
        PageRequest of = PageRequest.of(0, 7);

        // when
        Slice<NormalVoteData> actual = voteEntityRepository.findNormalVoteDataWithTime(of);

        // then
        assertThat(actual).hasSize(7)
                .extracting("title", "titleA")
                .containsExactly(
                        tuple("테스트 투표 10", "S1"),
                        tuple("테스트 투표 9", "Q1"),
                        tuple("테스트 투표 8", "O1"),
                        tuple("테스트 투표 7", "M1"),
                        tuple("테스트 투표 6", "K1"),
                        tuple("테스트 투표 5", "I1"),
                        tuple("테스트 투표 4", "G1")
                );


    }

}