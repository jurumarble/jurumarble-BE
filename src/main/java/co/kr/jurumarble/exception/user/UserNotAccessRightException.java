package co.kr.jurumarble.exception.user;

import co.kr.jurumarble.exception.StatusEnum;
import co.kr.jurumarble.exception.common.CustomException;

public class UserNotAccessRightException extends CustomException {

    private static final String message = "접근권한이 없는 유저입니다";
    private final StatusEnum status;

    public UserNotAccessRightException() {
        super(message);
        this.status = StatusEnum.ACCESS_RIGHT_FAILED;
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