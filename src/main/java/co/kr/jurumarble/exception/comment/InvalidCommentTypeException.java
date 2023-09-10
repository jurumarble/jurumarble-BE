package co.kr.jurumarble.exception.comment;

import co.kr.jurumarble.exception.StatusEnum;
import lombok.Getter;

@Getter
public class InvalidCommentTypeException extends RuntimeException {

    private final StatusEnum status;
    private static final String message = "적절한 CommentType 이 아닙니다.";

    public InvalidCommentTypeException() {
        super(message);
        this.status = StatusEnum.BAD_REQUEST;
    }

}
