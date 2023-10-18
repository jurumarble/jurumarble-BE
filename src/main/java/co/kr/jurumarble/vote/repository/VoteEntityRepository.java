package co.kr.jurumarble.vote.repository;

import co.kr.jurumarble.user.enums.AlcoholLimitType;
import co.kr.jurumarble.user.enums.ChoiceType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.domain.VoteContent;
import co.kr.jurumarble.vote.domain.VoteDrinkContent;
import co.kr.jurumarble.vote.dto.VoteData;
import co.kr.jurumarble.vote.repository.dto.HotDrinkVoteData;
import co.kr.jurumarble.vote.repository.dto.MyVotesCntData;
import co.kr.jurumarble.vote.repository.dto.VoteCommonData;
import co.kr.jurumarble.vote.repository.dto.VoteWithPostedUserCommonData;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoteEntityRepository {

    List<VoteCommonData> findVoteCommonDataByPopularity(String keyword, Pageable pageable);

    List<VoteContent> findVoteContentsByNormalVoteIds(List<Long> normalVoteIds);

    List<VoteDrinkContent> findVoteContentsByDrinkVoteIds(List<Long> drinkVoteIds);

    List<VoteCommonData> findVoteCommonDataByTime(String keyword, Pageable pageable);

    List<VoteData> findDrinkVotesByTime(String keyword, String region, int pageNum, int pageSize);

    List<VoteData> findDrinkVotesByPopularity(String keyword, String region, int pageNum, int pageSize);

    Optional<VoteWithPostedUserCommonData> findVoteCommonDataByVoteId(Long voteId);

    List<Vote> findByTitleContains(String keyword, int recommendCount);

    Long countByVoteAndChoiceAndGenderAndAgeAndMBTI(Long voteId, ChoiceType choiceType, GenderType gender, Integer classifyAge, MbtiType mbti, AlcoholLimitType alcoholLimit);

    Optional<HotDrinkVoteData> getHotDrinkVote(LocalDateTime nowTime);

    HotDrinkVoteData findOneDrinkVoteByPopular();

    List<VoteCommonData> findVoteCommonDataByParticipate(Long userId, int pageNum, int pageSize);

    List<VoteCommonData> findVoteCommonDataByPostedUserId(Long userId, int pageNum, int pageSize);

    List<VoteCommonData> findCommonVoteDataByBookmark(Long userId, int pageNum, int pageSize);

    Long findMyParticipatedVoteCnt(Long userId);
    Long findMyWrittenVoteCnt(Long userId);
    Long findMyBookmarkedVoteCnt(Long userId);
    List<VoteCommonData> findVoteCommonDataByTimeAndUserId(Long userId, String keyword, PageRequest pageable);
}
