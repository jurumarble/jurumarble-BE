package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.vote.dto.FindVoteListData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteEntityRepository {

    Page<FindVoteListData> findWithVoteWithPopular(PageRequest pageRequest);
}
