package co.kr.jurumarble.comment.enums;

import co.kr.jurumarble.common.enums.EnumModel;

public enum CommentType implements EnumModel {
    VOTES("투표"),
    DRINKS("우리술");

    private String value;

    CommentType(String value) {
        this.value = value;
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }
}