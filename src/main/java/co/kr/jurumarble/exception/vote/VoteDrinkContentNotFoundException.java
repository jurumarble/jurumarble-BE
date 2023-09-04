package co.kr.jurumarble.exception.vote;

import co.kr.jurumarble.exception.StatusEnum;
import co.kr.jurumarble.exception.common.CustomException;

public class VoteDrinkContentNotFoundException extends CustomException {

    private static final String message = "해당 투표 id와 연결된 전통주 컨텐츠 가 없습니다. 아이디 값을 다시 한번 확인하세요.";
    private final StatusEnum status;

    public VoteDrinkContentNotFoundException() {
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
