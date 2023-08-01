package co.kr.jurumarble.exception.user;

import co.kr.jurumarble.exception.StatusEnum;

public class AlreadyDeletedUserException extends RuntimeException{

    private final StatusEnum status;

    private static final String message = "유저의 정보가 이미 존재합니다";

    public AlreadyDeletedUserException(StatusEnum status) {
        super(message);
        this.status = status;
    }
}
