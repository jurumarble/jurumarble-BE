package co.kr.jurumarble.exception.user;

import co.kr.jurumarble.exception.StatusEnum;
import co.kr.jurumarble.exception.common.CustomException;

public class UserRejoinException extends CustomException {
    private static final String message = "재가입은 불가능 합니다";
    private final StatusEnum status;

    public UserRejoinException() {
        super(message);
        this.status = StatusEnum.USER_REJOIN_IMPOSSIBLE;
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
