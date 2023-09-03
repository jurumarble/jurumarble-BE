package co.kr.jurumarble.token.domain;

import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.domain.UserFinder;
import co.kr.jurumarble.user.dto.LoginToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenGenerator {

    private final UserFinder userFinder;
    private final JwtTokenProvider jwtTokenProvider;

    private final int MONTH_TO_MINITES = 43200;
    private final int ACCESS_TOKEN_EXPIREDTIME = 30;

    public LoginToken generate(String providerId, boolean isNewUser) {
        User user = userFinder.findByProviderId(providerId);

        return new LoginToken(
                generateAccessToken(user.getId()),
                generateRefreshToken(user.getId()),
                isNewUser
        );
    }

    private String generateRefreshToken(Long userId) {
        return jwtTokenProvider.makeJwtToken(userId, MONTH_TO_MINITES);
    }

    private String generateAccessToken(Long userId) {
        return jwtTokenProvider.makeJwtToken(userId, MONTH_TO_MINITES);
    }

}
