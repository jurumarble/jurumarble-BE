package co.kr.jurumarble.user.dto;

import co.kr.jurumarble.user.enums.AlcoholLimitType;
import co.kr.jurumarble.user.enums.MbtiType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateUserInfo {
    String imageUrl;
    String nickname;
    AlcoholLimitType alcoholLimit;
    MbtiType mbti;

    @Builder
    public UpdateUserInfo(String imageUrl, String nickname, AlcoholLimitType alcoholLimit, MbtiType mbti) {
        this.imageUrl = imageUrl;
        this.nickname = nickname;
        this.alcoholLimit = alcoholLimit;
        this.mbti = mbti;
    }
}
