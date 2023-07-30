package co.kr.jurumarble.user.domain;

import co.kr.jurumarble.exception.user.UserNotFoundException;
import co.kr.jurumarble.user.dto.AddUserInfo;
import co.kr.jurumarble.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserManager {
    private final UserRepository userRepository;

    public void addUserInfo(Long userId, AddUserInfo addUserInfo) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.addInfo(addUserInfo);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.deleteUser();
    }
}