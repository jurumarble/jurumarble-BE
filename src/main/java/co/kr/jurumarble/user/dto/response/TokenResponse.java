package co.kr.jurumarble.user.dto.response;

import co.kr.jurumarble.user.dto.LoginToken;
import lombok.Getter;

@Getter
public class TokenResponse {

    private String accessToken;
    private String refreshToken;
    private boolean isNewUser;

    public TokenResponse(LoginToken loginToken) {
        accessToken = loginToken.getAccessToken();
        refreshToken = loginToken.getRefreshToken();
        isNewUser = loginToken.isNewUser();
    }
}
