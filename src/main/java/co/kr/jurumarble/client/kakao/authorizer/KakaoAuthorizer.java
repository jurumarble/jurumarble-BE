package co.kr.jurumarble.client.kakao.authorizer;


import co.kr.jurumarble.client.common.ThirdPartyAuthorizer;
import co.kr.jurumarble.client.kakao.request.BearerAuthHeader;
import co.kr.jurumarble.client.kakao.response.KakaoTokenResponse;
import co.kr.jurumarble.client.kakao.response.KakaoUserInfo;
import co.kr.jurumarble.user.dto.ThirdPartySignupInfo;
import co.kr.jurumarble.user.enums.ProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class KakaoAuthorizer implements ThirdPartyAuthorizer {

    private final KakaoAuthClient kakaoAuthClient;
    private final KakaoApiClient kakaoApiClient;
    @Value("${spring.oauth2.client.registration.kakao.client-id}")
    private String clientId;
    @Value("${spring.oauth2.client.registration.kakao.client-secret}")
    private String client_secret;

    @Override
    public String getAccessToken(ThirdPartySignupInfo signupInfo) {
        Map<String, String> propertiesValues = signupInfo.getPropertiesValues();

        KakaoTokenResponse response = kakaoAuthClient.generateToken(
                "authorization_code",
                clientId,
                propertiesValues.get("redirectUrl"),
                propertiesValues.get("code"),
                client_secret
        );

        return response.getAccess_token();
    }

    @Override
    public Map<String, String> getUserInfo(String accessToken) {
        KakaoUserInfo kakaoUserInfo = kakaoApiClient.getUserInfo(new BearerAuthHeader(accessToken).getAuthorization());

        Map<String, String> result = new HashMap<>();
        result.put("id", kakaoUserInfo.getId().toString());
        result.put("nickname", kakaoUserInfo.getNickName());
        result.put("profile_image", kakaoUserInfo.getProfileImage());
        return result;
    }

    @Override
    public ProviderType getProviderType() {
        return ProviderType.KAKAO;
    }

}

