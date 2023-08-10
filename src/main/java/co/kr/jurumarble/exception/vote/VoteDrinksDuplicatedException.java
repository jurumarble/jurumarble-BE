package co.kr.jurumarble.exception.vote;

import co.kr.jurumarble.exception.StatusEnum;
import lombok.Getter;

@Getter
public class VoteDrinksDuplicatedException extends RuntimeException {
    private final StatusEnum status;

    private static final String message = "투표 후보로 중복된 두개의 전통주가 입력되었습니다.";

    public VoteDrinksDuplicatedException() {
        super(message);
        this.status = StatusEnum.VOTE_DRINKS_DUPLICATED;
    }
}
