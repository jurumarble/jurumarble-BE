package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.dto.NormalVoteData;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteEntityRepository {

    Slice<NormalVoteData> findNormalVoteDataWithPopularity(String keyword, Pageable pageable);

    Slice<NormalVoteData> findNormalVoteDataWithTime(String keyword, Pageable pageable);

    Optional<NormalVoteData> findNormalVoteDataByVoteId(Long voteId);

    List<Vote> findByTitleContains(String keyword);
}
