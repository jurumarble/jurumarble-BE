package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.user.enums.ChoiceType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.dto.VoteData;
import co.kr.jurumarble.vote.repository.dto.HotDrinkVoteData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoteEntityRepository {

    Slice<VoteData> findVoteDataWithPopularity(String keyword, Pageable pageable);

    Slice<VoteData> findVoteDataWithTime(String keyword, Pageable pageable);

    Optional<VoteData> findVoteDataByVoteId(Long voteId);

    List<Vote> findByTitleContains(String keyword);

    Long countByVoteAndChoiceAndGenderAndAgeAndMBTI(Long voteId, ChoiceType choiceType, GenderType gender, Integer classifyAge, MbtiType mbti);

    Optional<HotDrinkVoteData> getHotDrinkVote(LocalDateTime nowTime);

    HotDrinkVoteData findOneDrinkVoteByPopular();
}
