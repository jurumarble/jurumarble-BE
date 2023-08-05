package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.vote.dto.FindVoteListData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteEntityRepository {

    Page<FindVoteListData> findWithVoteWithPopular(PageRequest pageRequest);
}
