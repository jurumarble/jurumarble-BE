package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.config.JpaAuditionConfig;
import co.kr.jurumarble.vote.dto.FindVoteListData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import java.util.List;

@DataJpaTest
@Import(JpaAuditionConfig.class)
@TestPropertySource("classpath:application-dev.yml")
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
        PageRequest of = PageRequest.of(1, 1);

        // when
        List<FindVoteListData> withVoteResult = voteEntityRepository.findWithVoteResult(of);

        // then

    }

}