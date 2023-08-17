package co.kr.jurumarble.exception.drink;

import co.kr.jurumarble.exception.StatusEnum;
import co.kr.jurumarble.exception.common.CustomException;

public class DrinkNotFoundException extends CustomException {

    private static final String message = "전통주 아이디와 일치하는 술이 없습니다. 아이디를 다시 한번 확인하세요.";
    private final StatusEnum status;

    public DrinkNotFoundException() {
        super(message);
        this.status = StatusEnum.DRINK_NOT_FOUND;
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
