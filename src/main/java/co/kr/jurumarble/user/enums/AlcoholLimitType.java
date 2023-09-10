package co.kr.jurumarble.user.enums;

import co.kr.jurumarble.common.enums.EnumModel;

public enum AlcoholLimitType implements EnumModel {

    HIGH("상"),
    MEDIUM("중"),
    LOW("하");

    private String value;

    AlcoholLimitType(String value) {
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
