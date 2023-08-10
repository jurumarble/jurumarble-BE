package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.vote.dto.NormalVoteData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteEntityRepository {

    Page<NormalVoteData> findNormalVoteDataWithPopularity(PageRequest pageRequest);

    Page<NormalVoteData> findNormalVoteDataWithTime(PageRequest pageRequest);

    Optional<NormalVoteData> findNormalVoteDataByVoteId(Long voteId);

}
