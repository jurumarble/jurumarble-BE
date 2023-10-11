package co.kr.jurumarble.exception.notification;

import co.kr.jurumarble.exception.StatusEnum;
import co.kr.jurumarble.exception.common.CustomException;

public class AdminAuthorityException extends CustomException {

    private static final String message = "관리자만 해당 알림을 보낼 수 있습니다.";
    private final StatusEnum status;

    public AdminAuthorityException() {
        super(message);
        this.status = StatusEnum.AUTHORITY_EXCEPTION;
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
