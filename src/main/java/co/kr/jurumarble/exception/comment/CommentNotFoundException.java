package co.kr.jurumarble.exception.comment;

import co.kr.jurumarble.exception.StatusEnum;
import co.kr.jurumarble.exception.common.CustomException;
import lombok.Getter;

public class CommentNotFoundException extends CustomException {

    private final StatusEnum status;
    private static final String message = "해당 아이디를 가진 댓글이 없습니다. 아이디 값을 다시 한번 확인하세요.";
    public CommentNotFoundException() {
        super(message);
        this.status = StatusEnum.COMMENT_NOT_FOUND;
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