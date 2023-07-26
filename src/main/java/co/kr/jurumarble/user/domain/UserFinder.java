package co.kr.jurumarble.user.domain;

import co.kr.jurumarble.exception.user.UserNotFoundException;
import co.kr.jurumarble.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserFinder {
    private final UserRepository userRepository;

    public User findByProviderId(String providerId) {
        return userRepository.findByProviderId(providerId).orElseThrow(UserNotFoundException::new);
    }
}