package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.vote.dto.FindVoteListData;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteEntityRepository {

    List<FindVoteListData> findWithVoteResult(PageRequest pageRequest);
}
