package co.kr.jurumarble.vote.enums;

import co.kr.jurumarble.common.enums.EnumModel;

public enum SortByType implements EnumModel {
    ByTime("createdDate"),
    ByPopularity("인기순"),
    ByName("이름순");

    private String value;

    SortByType(String value) {
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
