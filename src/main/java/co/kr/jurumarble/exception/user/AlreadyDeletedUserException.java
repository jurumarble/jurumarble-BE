package co.kr.jurumarble.exception.user;

import co.kr.jurumarble.exception.StatusEnum;
import co.kr.jurumarble.exception.common.CustomException;

public class AlreadyDeletedUserException extends CustomException {

    private final StatusEnum status;

    private static final String message = "유저의 정보가 이미 존재합니다";

    public AlreadyDeletedUserException(StatusEnum status) {
        super(message);
        this.status = status;
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
