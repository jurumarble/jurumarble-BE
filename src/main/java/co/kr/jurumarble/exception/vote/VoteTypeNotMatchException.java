package co.kr.jurumarble.exception.vote;

import co.kr.jurumarble.exception.StatusEnum;
import lombok.Getter;

@Getter
public class VoteTypeNotMatchException extends IllegalArgumentException {

    private final StatusEnum status;

    private static final String message = "투표 타입이 일치하지 않습니다.";

    public VoteTypeNotMatchException() {
        super(message);
        this.status = StatusEnum.VOTE_TYPE_NOT_MATCH;
    }
}
