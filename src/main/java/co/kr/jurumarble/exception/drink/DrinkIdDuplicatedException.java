package co.kr.jurumarble.exception.drink;

import co.kr.jurumarble.exception.StatusEnum;
import co.kr.jurumarble.exception.common.CustomException;

public class DrinkIdDuplicatedException extends CustomException {
    private final StatusEnum status;

    private static final String message = "전통주 아이디가 중복됩니다. 전통주 아이디를 다시 한번 확인하세요.";

    public DrinkIdDuplicatedException() {
        super(message);
        this.status = StatusEnum.DRINK_ID_DUPLICATED;
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
