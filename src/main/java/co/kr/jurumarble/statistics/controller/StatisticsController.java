package co.kr.jurumarble.statistics.controller;

import co.kr.jurumarble.statistics.service.StatisticsService;
import co.kr.jurumarble.statistics.dto.VoteSelectResultData;
import co.kr.jurumarble.statistics.dto.response.SelectStatisticsResponse;
import co.kr.jurumarble.statistics.dto.response.TotalStatisticsResponse;
import co.kr.jurumarble.user.enums.AgeType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "statistics", description = "통계 api")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Operation(summary  = "투표 참여 인원 통계", description = "파라미터에 voteId 보내주시면 됩니다.")
    @GetMapping("/vote/{voteId}/total-statistics")
    public ResponseEntity<TotalStatisticsResponse> getTotalStatistics(@PathVariable("voteId") Long voteId) {

        Long totalVoteCount = statisticsService.getTotalStatistics(voteId);

        return new ResponseEntity(new TotalStatisticsResponse(voteId, totalVoteCount), HttpStatus.OK);
    }

    @Operation(summary = "A, B 투표 참여인원, 퍼센테이지 통계", description = "파라미터에 voteId, gender, age, mbti 보내주시면 됩니다.")
    @GetMapping("/vote/{voteId}/select-statistics")
    public ResponseEntity<SelectStatisticsResponse> getSelectStatistics(@PathVariable("voteId") Long voteId, @RequestParam(required = false) GenderType gender, @RequestParam(required = false) AgeType age, @RequestParam(required = false) MbtiType mbti) {

        VoteSelectResultData voteSelectResultData  = statisticsService.getSelectedStatistics(voteId, gender, age, mbti);

        return new ResponseEntity(new SelectStatisticsResponse(voteId, voteSelectResultData), HttpStatus.OK);
    }

}