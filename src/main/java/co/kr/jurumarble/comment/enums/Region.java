package co.kr.jurumarble.comment.enums;

public enum Region {
    SEOUL(1, "서울"),
    INCHEON(2, "인천"),
    DAEJEON(3, "대전"),
    DAEGU(4, "대구"),
    GWANGJU(5, "광주"),
    BUSAN(6, "부산"),
    ULSAN(7, "울산"),
    SEJONG(8, "세종"),
    GYEONGGI(31, "경기"),
    GANGWON(32, "강원"),
    CHUNGBUK(33, "충북"),
    CHUNGNAM(34, "충남"),
    GYEONGBUK(35, "경북"),
    GYEONGNAM(36, "경남"),
    JEONBUK(37, "전북"),
    JEONNAM(38, "전남"),
    JEJU(39, "제주"),
    ALL(null, "전국");

    private final Integer code;
    private final String name;

    Region(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}