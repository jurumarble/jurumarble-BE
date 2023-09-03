package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.user.enums.ChoiceType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.domain.VoteContent;
import co.kr.jurumarble.vote.domain.VoteDrinkContent;
import co.kr.jurumarble.vote.dto.VoteData;
import co.kr.jurumarble.vote.repository.dto.HotDrinkVoteData;
import co.kr.jurumarble.vote.repository.dto.VoteCommonData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoteEntityRepository {

    List<VoteCommonData> findVoteCommonDataByPopularity(String keyword, Pageable pageable);

    List<VoteContent> findVoteContentsByNormalVoteIds(List<Long> normalVoteIds);

    List<VoteDrinkContent> findVoteContentsByDrinkVoteIds(List<Long> drinkVoteIds);

    Slice<VoteData> findVoteDataWithTime(String keyword, Pageable pageable);

    Optional<VoteData> findVoteDataByVoteId(Long voteId);

    List<Vote> findByTitleContains(String keyword);

    Long countByVoteAndChoiceAndGenderAndAgeAndMBTI(Long voteId, ChoiceType choiceType, GenderType gender, Integer classifyAge, MbtiType mbti);

    Optional<HotDrinkVoteData> getHotDrinkVote(LocalDateTime nowTime);

    HotDrinkVoteData findOneDrinkVoteByPopular();
}
