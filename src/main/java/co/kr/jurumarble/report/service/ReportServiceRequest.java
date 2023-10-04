package co.kr.jurumarble.report.service;

import co.kr.jurumarble.report.domain.Report;
import co.kr.jurumarble.report.enums.ReportType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReportServiceRequest {

    private Long reportedObjectId;


    @Builder
    public ReportServiceRequest(Long reportedObjectId) {
        this.reportedObjectId = reportedObjectId;
    }


    public Report toReport(Long userId,Long reportedUserId, ReportType reportType) {
        return Report.builder()
                .reportUserId(userId)
                .reportedUserId(reportedUserId)
                .reportedObjectId(reportedObjectId)
                .reportType(reportType)
                .build();
    }
}
