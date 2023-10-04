package co.kr.jurumarble.report.dto.request;

import co.kr.jurumarble.report.service.ReportServiceRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportVoteRequest {
    @Schema(description = "신고할 투표의 아이디")
    @NotNull
    private Long reportedVoteId;

    @Builder
    public ReportVoteRequest(Long reportedObjectId) {
        this.reportedVoteId = reportedObjectId;
    }

    public ReportServiceRequest toServiceRequest() {
        return ReportServiceRequest.builder().reportedObjectId(reportedVoteId).build();
    }
}