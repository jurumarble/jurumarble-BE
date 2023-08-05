package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.config.JpaAuditionConfig;
import co.kr.jurumarble.vote.dto.FindVoteListData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;

import static org.assertj.core.groups.Tuple.tuple;

@DataJpaTest
@Import(JpaAuditionConfig.class)
@TestPropertySource(locations = "classpath:application-test.yml")
class VoteEntityRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;

    @Qualifier("voteEntityRepositoryImpl")
    @Autowired
    private VoteEntityRepository voteEntityRepository;

    @Autowired
    private EntityManager entityManager;

    @DisplayName("인기순으로 투표를 조회한다.")
    @Test
    void findWithVoteResult(){
        // given
        PageRequest of = PageRequest.of(0, 7);

        // when
        Page<FindVoteListData> actual = voteEntityRepository.findWithVoteWithPopular(of);

        // then
        Assertions.assertThat(actual).hasSize(7)
                .extracting(  "title", "voteNum")
                .containsExactly(
                        tuple("테스트 투표 1", 10L),
                        tuple("테스트 투표 3", 3L),
                        tuple("테스트 투표 2", 2L),
                        tuple("테스트 투표 4", 2L),
                        tuple("테스트 투표 5", 1L),
                        tuple("테스트 투표 6", 1L),
                        tuple("테스트 투표 7", 1L)
                );

        // 각 투표안의 투표 컨텐츠 객체 검증
        Assertions.assertThat(actual.getContent())
                .extracting(findVoteListData -> findVoteListData.getVoteContent().getTitleA())
                .containsExactly("A1", "E1", "C1", "G1", "I1", "K1", "M1");
    }

}