package co.kr.jurumarble.exception.user;

import co.kr.jurumarble.exception.StatusEnum;
import co.kr.jurumarble.exception.common.CustomException;

public class UserBirthYearMinorException extends CustomException {

    private static final String message = "주루마블은 만 19세 이상부터 회원가입이 가능합니다";
    private final StatusEnum status;

    public UserBirthYearMinorException() {
        super(message);
        this.status = StatusEnum.MINOR_JOIN_IMPOSSIBLE;
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
