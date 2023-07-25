package co.kr.jurumarble.enums;

import co.kr.jurumarble.common.enums.EnumModel;

public enum ChoiceType implements EnumModel {
    A("A"),
    B("B");

    private String value;

    ChoiceType(String value) {
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