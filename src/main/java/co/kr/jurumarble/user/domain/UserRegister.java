package co.kr.jurumarble.user.domain;

import co.kr.jurumarble.exception.user.UserRejoinException;
import co.kr.jurumarble.user.enums.ProviderType;
import co.kr.jurumarble.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegister {
    private final UserRepository userRepository;
    private final NicknameGenerator nicknameGenerator;

    /**
     * 일반 회원 가입
     */
    public void register(String providerId, ProviderType providerType) {
        if (userRepository.existsByProviderId(providerId)) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
        String randomNickName = nicknameGenerator.generateRandomNickName();
        User user = userOfWhenLogin(providerId, providerType, randomNickName);
        userRepository.save(user);
    }

    /**
     * 소셜 회원 가입
     */
    public boolean registerIfNeed(String providerId, ProviderType providerType) {
        if(userRepository.findByProviderIdAndDeletedDate2(providerId).isPresent()) {
            throw new UserRejoinException();
        }
        if (userRepository.existsByProviderId(providerId)) {
            return false;
        }
        String randomNickName = nicknameGenerator.generateRandomNickName();
        User user = userOfWhenLogin(providerId, providerType, randomNickName);
        userRepository.save(user);
        return true;
    }

    private User userOfWhenLogin(String providerId, ProviderType providerType, String nickName) {
        return User.builder()
                .nickname(nickName)
                .providerType(providerType)
                .providerId(providerId)
                .build();
    }

}