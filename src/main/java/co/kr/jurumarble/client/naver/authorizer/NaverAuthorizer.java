package co.kr.jurumarble.client.naver.authorizer;


import co.kr.jurumarble.client.common.ThirdPartyAuthorizer;
import co.kr.jurumarble.client.naver.request.BearerAuthHeader;
import co.kr.jurumarble.client.naver.response.NaverTokenResponse;
import co.kr.jurumarble.client.naver.response.NaverUserInfo;
import co.kr.jurumarble.user.dto.ThirdPartySignupInfo;
import co.kr.jurumarble.user.enums.ProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class NaverAuthorizer implements ThirdPartyAuthorizer {

    private final NaverAuthClient naverAuthClient;
    private final NaverApiClient naverApiClient;
    @Value("${spring.oauth2.client.registration.naver.client-id}")
    private String clientId;
    @Value("${spring.oauth2.client.registration.naver.client-secret}")
    private String client_secret;

    @Override
    public String getAccessToken(ThirdPartySignupInfo signupInfo) {

        Map<String, String> propertiesValues = signupInfo.getPropertiesValues();

        NaverTokenResponse response = naverAuthClient.generateToken(
                "authorization_code",
                clientId,
                client_secret,
                propertiesValues.get("code"),
                propertiesValues.get("state")
        );

        return response.getAccess_token();
    }

    @Override
    public Map<String, String> getUserInfo(String accessToken) {

        NaverUserInfo naverUserInfo = naverApiClient.getUserInfo(new BearerAuthHeader(accessToken).getAuthorization());

        Map<String, String> result = new HashMap<>();
        result.put("id", naverUserInfo.getId().toString());
//        result.put("nickname", naverUserInfo.getNickName());
//        result.put("profile_image", naverUserInfo.getProfileImage());

        return result;
    }

    @Override
    public ProviderType getProviderType() {
        return ProviderType.NAVER;
    }

}
