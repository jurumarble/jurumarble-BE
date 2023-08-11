package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.dto.NormalVoteData;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteEntityRepository {

    Slice<NormalVoteData> findVoteDataWithPopularity(PageRequest pageRequest);

    Slice<NormalVoteData> findVoteDataWithTime(PageRequest pageRequest);

    Optional<NormalVoteData> findVoteDataByVoteId(Long voteId);

    Slice<NormalVoteData> findVoteDataByTitleContainsPopularity(String keyword, PageRequest pageRequest);

    Slice<NormalVoteData> findVoteDataByTitleContainsWithTime(String keyword, PageRequest pageRequest);

    List<Vote> findByTitleContains(String keyword);
}
