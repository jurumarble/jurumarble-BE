package co.kr.jurumarble.exception.comment;

import co.kr.jurumarble.exception.StatusEnum;
import lombok.Getter;

@Getter
public class NoDataFoundException extends RuntimeException {

    private static final String message = "데이터가 존재하지 않습니다.";
    private final StatusEnum status;

    public NoDataFoundException() {
        super(message);
        this.status = StatusEnum.NO_CONTENT;
    }

}
