package co.kr.jurumarble.client.common;

import co.kr.jurumarble.user.dto.ThirdPartySignupInfo;
import co.kr.jurumarble.user.enums.ProviderType;

import java.util.Map;

public interface ThirdPartyAuthorizer {
    String getAccessToken(ThirdPartySignupInfo signupInfo);

    Map<String, String> getUserInfo(String accessToken);

    ProviderType getProviderType();

}