package co.kr.jurumarble.exception.user;

import co.kr.jurumarble.exception.StatusEnum;
import lombok.Getter;

@Getter
public class UserNotAccessRightException extends RuntimeException {

    private final StatusEnum status;

    private static final String message = "접근권한이 없는 유저입니다";

    public UserNotAccessRightException() {
        super(message);
        this.status = StatusEnum.ACCESS_RIGHT_FAILED;
    }
}