package co.kr.jurumarble.exception.report;

import co.kr.jurumarble.exception.StatusEnum;
import lombok.Getter;

@Getter
public class DuplicateReportException extends RuntimeException {

    private static final String message = "중복된 신고입니다.";
    private final StatusEnum status;

    public DuplicateReportException() {
        super(message);
        this.status = StatusEnum.DUPLICATE_REPORT;
    }
}
