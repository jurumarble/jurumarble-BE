package co.kr.jurumarble.user.dto.response;

import lombok.Getter;

@Getter
public class TokenTestResponse {
    private String accessToken;
    private String refreshToken;
    private boolean isNewUser;

    public TokenTestResponse(String token) {
        this.accessToken = token;
        this.refreshToken = token;
        this.isNewUser = true;
    }
}
