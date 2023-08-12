package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.dto.NormalVoteData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteEntityRepository {

    Slice<NormalVoteData> findNormalVoteDataWithPopularity(String keyword,PageRequest pageRequest);

    Slice<NormalVoteData> findNormalVoteDataWithTime(PageRequest pageRequest);

    Optional<NormalVoteData> findNormalVoteDataByVoteId(Long voteId);

    Slice<NormalVoteData> findVoteDataByTitleContainsWithTime(String keyword, PageRequest pageRequest);

    List<Vote> findByTitleContains(String keyword);
}
