package co.kr.jurumarble.exception.user;

import co.kr.jurumarble.exception.StatusEnum;
import co.kr.jurumarble.exception.common.CustomException;

public class BirthYearLimitExceededException extends CustomException {

    private static final String message = "출생년도를 올바르게 입력하세요. 유효한 출생년도는 1901년 이후입니다.";
    private final StatusEnum status;

    public BirthYearLimitExceededException() {
        super(message);
        this.status = StatusEnum.BIRTH_YEAR_LIMIT_EXCEEDED;
    }

    @Override
    public StatusEnum getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
