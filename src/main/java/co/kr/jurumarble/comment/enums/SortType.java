package co.kr.jurumarble.comment.enums;

import co.kr.jurumarble.common.enums.EnumModel;
import co.kr.jurumarble.exception.comment.InvalidSortingMethodException;

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

    public static SortType fromValue(String value) {
        for (SortType type : SortType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        throw new InvalidSortingMethodException();
    }
}
