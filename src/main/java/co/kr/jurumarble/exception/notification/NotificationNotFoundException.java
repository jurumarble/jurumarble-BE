package co.kr.jurumarble.exception.notification;

import co.kr.jurumarble.exception.StatusEnum;
import co.kr.jurumarble.exception.common.CustomException;

public class NotificationNotFoundException extends CustomException {

    private static final String message = "해당 알림이 없습니다. 아이디 값을 다시 한번 확인하세요.";
    private final StatusEnum status;

    public NotificationNotFoundException() {
        super(message);
        this.status = StatusEnum.NOTIFICATION_NOT_FOUND;
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
