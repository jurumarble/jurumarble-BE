package co.kr.jurumarble.user.domain;

import co.kr.jurumarble.user.enums.ProviderType;
import co.kr.jurumarble.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegister {
    private final UserRepository userRepository;

    /**
     * 일반 회원 가입
     */
    public void register(String providerId, ProviderType providerType) {
        if (userRepository.existsByProviderId(providerId)) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
        User user = userOfWhenLogin(providerId, providerType);
        userRepository.register(user);
    }

    /**
     * 소셜 회원 가입
     */
    public boolean registerIfNeed(String providerId, ProviderType providerType) {
        if (userRepository.existsByProviderId(providerId)) {
            return false;
        }
        User user = userOfWhenLogin(providerId, providerType);
        userRepository.register(user);
        return true;
    }

    private User userOfWhenLogin(String providerId, ProviderType providerType) {
        return User.builder()
                .providerId(providerId)
                .providerType(providerType)
                .build();
    }
}