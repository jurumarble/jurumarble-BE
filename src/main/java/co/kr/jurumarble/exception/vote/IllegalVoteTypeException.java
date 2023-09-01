package co.kr.jurumarble.exception.vote;

import co.kr.jurumarble.exception.StatusEnum;
import co.kr.jurumarble.exception.common.CustomException;

public class IllegalVoteTypeException extends CustomException {

    private static final String message = "투표 타입이 유효하지 않은 투표가 존재합니다.";
    private final StatusEnum status;

    public IllegalVoteTypeException() {
        super(message);
        this.status = StatusEnum.ALREADY_VOTE_RESULT_EXIST;
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
