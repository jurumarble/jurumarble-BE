package co.kr.jurumarble.exception.user;

import co.kr.jurumarble.exception.StatusEnum;
import lombok.Getter;

@Getter
public class AlreadyExistUserException extends RuntimeException {

    private final StatusEnum status;

    private static final String message = "삭제된 유저는 조회 할 수 없습니다.";

    public AlreadyExistUserException(StatusEnum status) {
        super(message);
        this.status = status;
    }
}