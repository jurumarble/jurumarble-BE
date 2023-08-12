package co.kr.jurumarble.exception.common;

import co.kr.jurumarble.exception.StatusEnum;

public abstract class CustomException extends RuntimeException{

    public abstract StatusEnum getStatus();

    public abstract String getMessage();

    public CustomException(String message) {
        super(message);
    }
}
