package co.kr.jurumarble.exception.vote;

import co.kr.jurumarble.exception.StatusEnum;
import co.kr.jurumarble.exception.common.CustomException;

public class AlreadyUserDoVoteException extends CustomException {

    private static final String message = "동일 유저가 해당 투표를 이미 참여했습니다";
    private final StatusEnum status;

    public AlreadyUserDoVoteException() {
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
