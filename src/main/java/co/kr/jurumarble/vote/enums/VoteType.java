package co.kr.jurumarble.vote.enums;

import co.kr.jurumarble.common.enums.EnumModel;

public enum VoteType implements EnumModel {
    NORMAL("일반투표"),
    DRINK("전통주투표");

    private String value;

    VoteType(String value) {
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
