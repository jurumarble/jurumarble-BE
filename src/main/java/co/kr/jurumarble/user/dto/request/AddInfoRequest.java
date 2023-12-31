package co.kr.jurumarble.user.dto.request;

import co.kr.jurumarble.user.dto.AddUserInfo;
import co.kr.jurumarble.user.enums.AlcoholLimitType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddInfoRequest {

    @NotBlank(message = "mbti는 blank일 수 없습니다.")
    private MbtiType mbti;

    @NotBlank(message = "birthOfAge는 blank일 수 없습니다.")
    private Integer birthOfAge;

    @NotBlank(message = "gender는 blank일 수 없습니다.")
    private GenderType gender;

    @NotBlank(message = "alcohol_limit(주량)은 blank일 수 없습니다.")
    private AlcoholLimitType alcoholLimit;

    public AddUserInfo toAddUserInfo() {
        return new AddUserInfo(mbti, birthOfAge, gender, alcoholLimit);
    }

}
