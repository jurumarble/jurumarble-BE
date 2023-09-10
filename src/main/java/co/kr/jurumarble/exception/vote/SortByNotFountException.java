package co.kr.jurumarble.exception.vote;

import co.kr.jurumarble.exception.StatusEnum;
import co.kr.jurumarble.exception.common.CustomException;

public class SortByNotFountException extends CustomException {

    private static final String message = "해당 정렬 기준은 없습니다 정렬 기준을 다시 확인해 주세요";
    private final StatusEnum status;

    public SortByNotFountException() {
        super(message);
        this.status = StatusEnum.VOTE_NOT_FOUND;
    }

    @Override
    public StatusEnum getStatus() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
