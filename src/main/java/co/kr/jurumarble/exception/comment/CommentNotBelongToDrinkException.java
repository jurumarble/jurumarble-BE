package co.kr.jurumarble.exception.comment;

import co.kr.jurumarble.exception.StatusEnum;
import lombok.Getter;

@Getter
public class CommentNotBelongToDrinkException extends RuntimeException {

    private static final String message = "댓글이 해당 술인포에 있는 댓글이 아닙니다.";
    private final StatusEnum status;

    public CommentNotBelongToDrinkException() {
        super(message);
        this.status = StatusEnum.COMMENT_NOT_BELONG_TO_VOTE;
    }


}
