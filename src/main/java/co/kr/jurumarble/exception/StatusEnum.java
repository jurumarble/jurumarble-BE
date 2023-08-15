package co.kr.jurumarble.exception;

import lombok.Getter;

@Getter
public enum StatusEnum {
    BAD_REQUEST(400, "BAD_REQUEST"),
    VOTE_TYPE_NOT_MATCH(400,"VOTE_TYPE_NOT_MATCH"),
    VOTE_DRINKS_DUPLICATED(400,"VOTE_DRINK_DUPLICATED"),
    TOKEN_EXPIRED(401, "TOKEN_EXPIRED"),
    VOTE_NOT_FOUND(404, "VOTE_NOT_FOUND"),
    DRINK_NOT_FOUND(404, "DRINK_NOT_FOUND"),
    DRINK_ID_DUPLICATED(400,"DRINK_ID_DUPLICATED"),
    USER_NOT_FOUND(404, "USER_NOT_FOUND"),
    COMMENT_NOT_FOUND(404, "COMMENT_NOT_FOUND"),
    ALREADY_USER_EXIST(403, "ALREADY_USER_EXIST"),
    ALREADY_VOTE_RESULT_EXIST(403, "ALREADY_VOTE_RESULT_EXIST"),
    TOKEN_NOT_EXIST(404, "TOKEN_NOT_EXIST"),
    ACCESS_RIGHT_FAILED(412, "ACCESS_RIGHT_FAILED");


    private final int statusCode;
    private final String code;

    private StatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}