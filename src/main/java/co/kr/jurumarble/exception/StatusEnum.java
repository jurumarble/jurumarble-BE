package co.kr.jurumarble.exception;

import lombok.Getter;

@Getter
public enum StatusEnum {
    BAD_REQUEST(400, "BAD_REQUEST"),
    VOTE_TYPE_NOT_MATCH(400, "VOTE_TYPE_NOT_MATCH"),
    VOTE_DRINKS_DUPLICATED(400, "VOTE_DRINK_DUPLICATED"),
    TOKEN_EXPIRED(401, "TOKEN_EXPIRED"),
    VOTE_NOT_FOUND(404, "VOTE_NOT_FOUND"),
    VOTE_RESULT_NOT_FOUND(404, "VOTE_RESULT_NOT_FOUND"),
    DRINK_NOT_FOUND(404, "DRINK_NOT_FOUND"),
    DRINK_ID_DUPLICATED(400, "DRINK_ID_DUPLICATED"),
    ALREADY_ENJOYED_DRINK(400, "ALREADY_ENJOYED_DRINK"),
    USER_NOT_FOUND(404, "USER_NOT_FOUND"),
    COMMENT_NOT_FOUND(404, "COMMENT_NOT_FOUND"),
    ALREADY_USER_EXIST(403, "ALREADY_USER_EXIST"),
    ALREADY_VOTE_RESULT_EXIST(403, "ALREADY_VOTE_RESULT_EXIST"),
    TOKEN_NOT_EXIST(404, "TOKEN_NOT_EXIST"),
    COMMENT_NOT_BELONG_TO_USER(400, "COMMENT_NOT_BELONG_TO_USER"),
    COMMENT_NOT_BELONG_TO_VOTE(400, "COMMENT_NOT_BELONG_TO_USER"),
    NO_CONTENT(204, "NO_CONTENT"),
    COMMENT_EMOTION_NOT_FOUND(404, "COMMENT_EMOTION_NOT_FOUND"),
    ACCESS_RIGHT_FAILED(412, "ACCESS_RIGHT_FAILED"),
    USER_REJOIN_IMPOSSIBLE(409, "USER_REJOIN_IMPOSSIBLE"),
    NOTIFICATION_NOT_FOUND(404, "NOTIFICATION_NOT_FOUND"),
    SELF_REPORT_NOT_ALLOWED(400, "SELF_REPORT_NOT_ALLOWED"),
    DUPLICATE_REPORT(400,"DUPLICATE_REPORT_NOT_ALLOWED" ),
    MINOR_JOIN_IMPOSSIBLE(403, "MINOR_JOIN_IMPOSSIBLE"),
    BIRTH_YEAR_LIMIT_EXCEEDED(400, "BIRTH_YEAR_LIMIT_EXCEEDED"),
    AUTHORITY_EXCEPTION(403, "AUTHORITY_EXCEPTION");



    private final int statusCode;
    private final String code;

    private StatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }
}