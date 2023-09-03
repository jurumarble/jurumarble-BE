package co.kr.jurumarble.exception.comment;

import co.kr.jurumarble.exception.StatusEnum;
import lombok.Getter;

@Getter
public class NestedCommentNotAllowedException extends RuntimeException {

    private static final String message = "대댓글에는 대댓글을 달 수 없습니다.";
    private final StatusEnum status;

    public NestedCommentNotAllowedException() {
        super(message);
        this.status = StatusEnum.BAD_REQUEST;
    }


}
