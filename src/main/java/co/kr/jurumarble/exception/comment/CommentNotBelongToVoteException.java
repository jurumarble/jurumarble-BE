package co.kr.jurumarble.exception.comment;

import co.kr.jurumarble.exception.StatusEnum;
import lombok.Getter;

@Getter
public class CommentNotBelongToVoteException extends RuntimeException{

    private final StatusEnum status;
    private static final String message = "댓글이 해당 투표에 있는 댓글이 아닙니다.";
    public CommentNotBelongToVoteException() {
        super(message);
        this.status = StatusEnum.COMMENT_NOT_BELONG_TO_VOTE;
    }


}