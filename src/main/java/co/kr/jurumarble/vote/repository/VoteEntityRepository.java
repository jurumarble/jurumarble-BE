package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.vote.dto.VoteData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteEntityRepository {

    Page<VoteData> findVoteDataWithPopularity(PageRequest pageRequest);

    Page<VoteData> findVoteDataWithTime(PageRequest pageRequest);

    Optional<VoteData> findVoteDataByVoteId(Long voteId);

}
