package co.kr.jurumarble.user.dto.response;

import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.enums.AgeType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import lombok.Getter;

@Getter
public class GetUserResponse {

    private final String nickname;
    private final String email;
    private final String imageUrl;
    private final AgeType ageType;
    private final GenderType gender;
    private final MbtiType mbti;

    public GetUserResponse(User user) {
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.imageUrl = user.getImageUrl();
        this.ageType = user.classifyAge();
        this.gender = user.getGender();
        this.mbti = user.getMbti();
    }
}
