package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.vote.dto.VoteData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteEntityRepository {

    Slice<VoteData> findVoteDataWithPopularity(PageRequest pageRequest);

    Slice<VoteData> findVoteDataWithTime(PageRequest pageRequest);

    Optional<VoteData> findVoteDataByVoteId(Long voteId);

}
