package co.kr.jurumarble.exception.user;

import co.kr.jurumarble.exception.StatusEnum;
import co.kr.jurumarble.exception.common.CustomException;

public class UserIllegalStateException extends CustomException {
    private final StatusEnum status;
    private static final String message = "MBTI 수정 후 2개월 내에 수정할 수 없습니다.";

    public UserIllegalStateException() {
        super(message);
        this.status = StatusEnum.BAD_REQUEST;
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