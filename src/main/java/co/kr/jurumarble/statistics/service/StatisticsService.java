package co.kr.jurumarble.statistics.service;

import co.kr.jurumarble.exception.vote.VoteNotFoundException;
import co.kr.jurumarble.statistics.dto.VoteSelectResultData;
import co.kr.jurumarble.user.enums.AgeType;
import co.kr.jurumarble.user.enums.ChoiceType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.repository.VoteRepository;
import co.kr.jurumarble.vote.repository.VoteResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class StatisticsService {

    private final VoteResultRepository voteResultRepository;
    private final VoteRepository voteRepository;

    public Long getTotalStatistics(Long voteId) {

        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        Long totalVoteCount = voteResultRepository.countByVoteId(voteId);

        return totalVoteCount;
    }

    public VoteSelectResultData getSelectedStatistics(Long voteId, GenderType gender, AgeType age, MbtiType mbti) {

        Integer classifyAge = null;

        if(age != null)
            classifyAge = Integer.valueOf(age.getValue().substring(0, 2));

        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        Long totalCountA = voteRepository.countByVoteAndChoiceAndGenderAndAgeAndMBTI(voteId, ChoiceType.A, gender, classifyAge, mbti);
        Long totalCountB = voteRepository.countByVoteAndChoiceAndGenderAndAgeAndMBTI(voteId, ChoiceType.B, gender, classifyAge, mbti);

        float totalVoteCount = totalCountA + totalCountB;

        int percentA = (int) (((float)totalCountA / totalVoteCount) * 100);
        int percentB = (int) (((float)totalCountB / totalVoteCount) * 100);

        VoteSelectResultData voteSelectResultData = new VoteSelectResultData(totalCountA, totalCountB, percentA, percentB);

        return voteSelectResultData;
    }

}