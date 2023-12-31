package co.kr.jurumarble.exception.user;

import co.kr.jurumarble.exception.StatusEnum;
import co.kr.jurumarble.exception.common.CustomException;

public class AlreadyExistUserException extends CustomException {

    private static final String message = "삭제된 유저는 조회 할 수 없습니다.";
    private final StatusEnum status;

    public AlreadyExistUserException(StatusEnum status) {
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