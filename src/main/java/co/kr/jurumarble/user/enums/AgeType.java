package co.kr.jurumarble.user.enums;

import co.kr.jurumarble.common.enums.EnumModel;

public enum AgeType implements EnumModel {
    teenager("10대"),
    twenties("20대"),
    thirties("30대"),
    fourties("40대"),
    fifties("50대");

    private String value;

    AgeType(String value) {
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
