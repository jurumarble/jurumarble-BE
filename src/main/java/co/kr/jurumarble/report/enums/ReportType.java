package co.kr.jurumarble.report.enums;

import co.kr.jurumarble.common.enums.EnumModel;

public enum ReportType implements EnumModel {
    Comment("댓글"),
    Vote("투표");

    private String value;

    ReportType(String value) {
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
