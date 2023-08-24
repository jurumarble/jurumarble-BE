package co.kr.jurumarble.exception.token;

import co.kr.jurumarble.exception.StatusEnum;
import co.kr.jurumarble.exception.common.CustomException;

public class TokenNotFoundException extends CustomException {
    private static final String message = "해당 아이디를 가진 유저가 없습니다. 아이디 값을 다시 한번 확인하세요.";
    private final StatusEnum status;

    public TokenNotFoundException() {
        super(message);
        this.status = StatusEnum.TOKEN_NOT_EXIST;
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