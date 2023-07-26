package co.kr.jurumarble.user.enums;

import co.kr.jurumarble.common.enums.EnumModel;

public enum ProviderType implements EnumModel {

    GOOGLE("google"),
    KAKAO("kakao"),
    NAVER("naver"),

    NORMAL("일반회원가입");


    private final String value;

    ProviderType(String value) {
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
