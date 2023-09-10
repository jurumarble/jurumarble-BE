package co.kr.jurumarble.exception.vote;

import co.kr.jurumarble.exception.StatusEnum;
import co.kr.jurumarble.exception.common.CustomException;

public class VoteResultNotFoundException extends CustomException {

    private static final String message = "투표 결과가 없습니다.";
    private final StatusEnum status;

    public VoteResultNotFoundException() {
        super(message);
        this.status = StatusEnum.VOTE_RESULT_NOT_FOUND;
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