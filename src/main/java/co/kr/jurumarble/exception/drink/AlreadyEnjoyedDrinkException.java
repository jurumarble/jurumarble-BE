package co.kr.jurumarble.exception.drink;

import co.kr.jurumarble.exception.StatusEnum;
import co.kr.jurumarble.exception.common.CustomException;

public class AlreadyEnjoyedDrinkException extends CustomException {
    private static final String message = "이미 즐겼어요 처리된 전통주 입니다.";
    private final StatusEnum status;

    public AlreadyEnjoyedDrinkException() {
        super(message);
        this.status = StatusEnum.ALREADY_ENJOYED_DRINK;
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
