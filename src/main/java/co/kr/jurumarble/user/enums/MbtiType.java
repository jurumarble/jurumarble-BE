package co.kr.jurumarble.user.enums;

import co.kr.jurumarble.common.enums.EnumModel;

public enum MbtiType implements EnumModel {

    ENFJ("enfj"),
    ENFP("enfp"),
    ENTJ("entj"),
    ENTP("entp"),
    ESFJ("esfj"),
    ESFP("esfp"),
    ESTJ("estj"),
    ESTP("estp"),
    INFJ("infj"),
    INFP("infp"),
    INTJ("intj"),
    INTP("intp"),
    ISFJ("isfj"),
    ISFP("isfp"),
    ISTJ("istj"),
    ISTP("istp"),
    NULL("null");

    private String value;

    MbtiType(String value) {
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