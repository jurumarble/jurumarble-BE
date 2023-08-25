package co.kr.jurumarble.exception.comment;

import co.kr.jurumarble.exception.StatusEnum;
import lombok.Getter;

@Getter
public class CommentNotBelongToUserException extends RuntimeException {

    private final StatusEnum status;
    private static final String message = "본인이 작성한 댓글이 아닙니다.";

    public CommentNotBelongToUserException() {
        super(message);
        this.status = StatusEnum.COMMENT_NOT_BELONG_TO_USER;
    }

}
