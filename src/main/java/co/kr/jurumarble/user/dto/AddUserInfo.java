package co.kr.jurumarble.user.dto;

import co.kr.jurumarble.enums.GenderType;
import co.kr.jurumarble.enums.MbtiType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddUserInfo {

    private MbtiType mbti;

    private Integer age;

    private GenderType gender;

}
