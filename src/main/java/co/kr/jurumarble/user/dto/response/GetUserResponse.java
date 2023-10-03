package co.kr.jurumarble.user.dto.response;

import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.enums.AgeType;
import co.kr.jurumarble.user.enums.AlcoholLimitType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import lombok.Getter;

@Getter
public class GetUserResponse {

    private final Long userId;
    private final String nickname;
    private final String email;
    private final String imageUrl;
    private final Integer yearOfBirth;
    private final GenderType gender;
    private final MbtiType mbti;
    private final AlcoholLimitType alcoholLimit;

    public GetUserResponse(User user) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.imageUrl = user.getImageUrl();
        this.yearOfBirth = user.getYearOfBirth();
        this.gender = user.getGender();
        this.mbti = user.getMbti();
        this.alcoholLimit = user.getAlcoholLimit();
    }
}
