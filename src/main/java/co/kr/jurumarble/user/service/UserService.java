package co.kr.jurumarble.user.service;

import co.kr.jurumarble.client.common.ThirdPartyAuthorizer;
import co.kr.jurumarble.client.common.ThirdPartyAuthorizerProvider;
import co.kr.jurumarble.token.domain.TokenGenerator;
import co.kr.jurumarble.user.domain.UserManager;
import co.kr.jurumarble.user.domain.UserRegister;
import co.kr.jurumarble.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRegister userRegister;
    private final ThirdPartyAuthorizerProvider thirdPartyAuthorizerProvider;
    private final TokenGenerator tokenGenerator;
    private final UserManager userManager;

    public void signup(SocialLoginInfo socialLoginInfo) {
        userRegister.register(socialLoginInfo.getProviderId(), socialLoginInfo.getProviderType());
    }

    public LoginToken signupByThirdParty(ThirdPartySignupInfo signupInfo) {
        String providerId = requestProviderIdFromThirdParty(signupInfo);

        boolean isNewUser = userRegister.registerIfNeed(providerId, signupInfo.getProviderType());
        return tokenGenerator.generate(providerId, isNewUser);
    }

    private String requestProviderIdFromThirdParty(ThirdPartySignupInfo signupInfo) {
        ThirdPartyAuthorizer authorizer = thirdPartyAuthorizerProvider.get(signupInfo.getProviderType());
        String accessToken = authorizer.getAccessToken(signupInfo);
        Map<String, String> userInfo = authorizer.getUserInfo(accessToken);
        return userInfo.get("id");
    }

    public void addUserInfo(Long userId, AddUserInfo addUserInfo) {
        userManager.addUserInfo(userId, addUserInfo);
    }

    public void deleteUser(Long userId) {
        userManager.deleteUser(userId);
    }
}
