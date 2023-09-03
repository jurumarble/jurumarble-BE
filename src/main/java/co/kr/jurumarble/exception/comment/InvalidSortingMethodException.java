package co.kr.jurumarble.exception.comment;

import co.kr.jurumarble.exception.StatusEnum;
import lombok.Getter;

@Getter
public class InvalidSortingMethodException extends IllegalArgumentException {

    private static final String message = "적절한 정렬 방식이 아닙니다.";
    private final StatusEnum status;

    public InvalidSortingMethodException() {
        super(message);
        this.status = StatusEnum.BAD_REQUEST;
    }
}
