package co.kr.jurumarble.exception.vote;

import co.kr.jurumarble.exception.StatusEnum;
import co.kr.jurumarble.exception.common.CustomException;

public class VoteNotFoundException extends CustomException {

    private final StatusEnum status;

    private static final String message = "해당 id를 가진 투표가 없습니다. 아이디 값을 다시 한번 확인하세요.";

    public VoteNotFoundException() {
        super(message);
        this.status = StatusEnum.VOTE_NOT_FOUND;
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