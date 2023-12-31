package co.kr.jurumarble.exception.vote;

import co.kr.jurumarble.exception.StatusEnum;
import co.kr.jurumarble.exception.common.CustomException;

public class VoteContentNotFoundException extends CustomException {

    private static final String message = "해당 id를 가진 투표 컨텐츠가 없습니다. 아이디 값을 다시 한번 확인하세요.";
    private final StatusEnum status;

    public VoteContentNotFoundException() {
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
