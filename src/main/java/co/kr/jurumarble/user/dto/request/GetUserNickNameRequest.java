package co.kr.jurumarble.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 유저 랜덤 생성 외부 api 사용시 데이터 받아올 포맷 dto
 */
@Getter
@NoArgsConstructor
public class GetUserNickNameRequest {
    private String[] words;
    private String seed;
}