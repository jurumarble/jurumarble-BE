package co.kr.jurumarble.drink.enums;

import co.kr.jurumarble.common.enums.EnumModel;

public enum Region implements EnumModel {
    SEOUL("서울"),
    GYEONGGI("경기"),
    BUSAN("부산"),
    DAEGU("대구"),
    GWANJU("광주"),
    INCHEON("인천"),
    DAEJEON("대전"),
    ULSAN("울산"),
    GANGWON("강원"),
    CHUNGBUK("충북"),
    CHUNGNAM("충남"),
    JEONBUK("전북"),
    JEONNAM("전남"),
    GYEONGBUK("경북"),
    GYEONGNAM("경남"),
    JEJU("제주");

    private String value;

    Region(String value) {
        this.value = value;
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public String getValue() {
        return null;
    }
}
