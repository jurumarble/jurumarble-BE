package co.kr.jurumarble.comment.enums;

import co.kr.jurumarble.common.enums.EnumModel;

public enum SortType implements EnumModel {
    ByTime("최신순"),
    ByPopularity("인기순");

    private String value;

    SortType(String value) {
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
