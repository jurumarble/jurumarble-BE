package co.kr.jurumarble.exception.user;

import co.kr.jurumarble.exception.StatusEnum;
import co.kr.jurumarble.exception.common.CustomException;

public class AlreadyDeletedUserException extends CustomException {

    private static final String message = "유저의 정보가 이미 존재합니다";
    private final StatusEnum status;

    public AlreadyDeletedUserException() {
        super(message);
        this.status = StatusEnum.ALREADY_USER_EXIST;
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
