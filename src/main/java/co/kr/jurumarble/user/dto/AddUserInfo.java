package co.kr.jurumarble.user.dto;

import co.kr.jurumarble.user.enums.AlcoholLimitType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddUserInfo {

    private MbtiType mbti;

    private Integer birthOfAge;

    private GenderType gender;

    private AlcoholLimitType alcoholLimit;

}
