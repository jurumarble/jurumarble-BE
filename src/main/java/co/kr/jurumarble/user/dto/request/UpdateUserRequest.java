package co.kr.jurumarble.user.dto.request;

import co.kr.jurumarble.user.dto.UpdateUserInfo;
import co.kr.jurumarble.user.enums.AlcoholLimitType;
import co.kr.jurumarble.user.enums.MbtiType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class UpdateUserRequest {

    @NotBlank(message = "프로필 사진은 필수값 입니다")
    String imageUrl;
    @NotBlank(message = "닉네임은 필수값 입니다")
    String nickname;
    @NotNull(message = "주량은 필수값 입니다")
    AlcoholLimitType alcoholLimit;
    @NotNull(message = "mbti는 필수값 입니다")
    MbtiType mbti;


    public UpdateUserInfo toUpdateUserInfo() {
        return UpdateUserInfo.builder()
                .imageUrl(imageUrl)
                .nickname(nickname)
                .alcoholLimit(alcoholLimit)
                .mbti(mbti)
                .build();
    }
}
