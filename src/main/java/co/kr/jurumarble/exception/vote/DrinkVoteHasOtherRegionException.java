package co.kr.jurumarble.exception.vote;

import co.kr.jurumarble.exception.StatusEnum;
import co.kr.jurumarble.exception.common.CustomException;

public class DrinkVoteHasOtherRegionException extends CustomException {

    private static final String message = "전통주 투표에 다른 지역의 술이 들어갔습니다.";
    private final StatusEnum status;

    public DrinkVoteHasOtherRegionException() {
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
