package co.kr.jurumarble.exception.report;

import co.kr.jurumarble.exception.StatusEnum;
import lombok.Getter;

@Getter
public class SelfReportNotAllowedException extends RuntimeException {
    private static final String message = "본인의 투표 및 댓글에 신고할 수 없습니다.";
    private final StatusEnum status;

    public SelfReportNotAllowedException() {
        super(message);
        this.status = StatusEnum.SELF_REPORT_NOT_ALLOWED;
    }
}
