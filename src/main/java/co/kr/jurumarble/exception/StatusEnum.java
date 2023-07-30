package co.kr.jurumarble.exception;

import lombok.Getter;

@Getter
public enum StatusEnum {
    BAD_REQUEST(400, "BAD_REQUEST"),
    USER_NOT_FOUND(404, "USER_NOT_FOUND"),
    VOTE_NOT_FOUND(404, "VOTE_NOT_FOUND"),
    COMMENT_NOT_FOUND(404, "COMMENT_NOT_FOUND"),
    COMMENT_EMOTION_NOT_FOUND(404, "COMMENT_EMOTION_NOT_FOUND"),
    ALREADY_VOTE_RESULT_EXIST(403, "ALREADY_VOTE_RESULT_EXIST"),
    TOKEN_NOT_EXIST(404, "TOKEN_NOT_EXIST"),
    TOKEN_EXPIRED(401, "TOKEN_EXPIRED"),
    ACCESS_RIGHT_FAILED(412, "ACCESS_RIGHT_FAILED");


    private final int statusCode;
    private final String code;

    private StatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}