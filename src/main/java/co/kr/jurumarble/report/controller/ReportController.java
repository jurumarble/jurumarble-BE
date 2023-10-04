package co.kr.jurumarble.report.controller;

import co.kr.jurumarble.report.dto.request.ReportCommentRequest;
import co.kr.jurumarble.report.dto.request.ReportVoteRequest;
import co.kr.jurumarble.report.enums.ReportType;
import co.kr.jurumarble.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/reports")
@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "report", description = "report api")
public class ReportController {
    private final ReportService reportService;

    @Operation(summary = "댓글 신고 기능", description = "헤더에 토큰을 포함하고, 요청 바디에 'reportedUserId'(신고할 유저의 아이디)를 JSON 형식으로 보내주세요.")
    @PostMapping("/comments")
    public ResponseEntity reportComment(@Valid @RequestBody ReportCommentRequest reportCommentRequest, @RequestAttribute Long userId) {
        reportService.reportComment(reportCommentRequest.toServiceRequest(), userId, ReportType.Comment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "투표 신고 기능", description = "헤더에 토큰을 포함하고, 요청 바디에 'reportedVoteId'(신고할 투표의 아이디)를 JSON 형식으로 보내주세요.")
    @PostMapping("/votes")
    public ResponseEntity reportVote(@Valid @RequestBody ReportVoteRequest reportVoteRequest, @RequestAttribute Long userId) {
        reportService.reportVote(reportVoteRequest.toServiceRequest(), userId, ReportType.Vote);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
