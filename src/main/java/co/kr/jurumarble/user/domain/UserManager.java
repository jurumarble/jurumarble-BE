package co.kr.jurumarble.user.domain;

import co.kr.jurumarble.user.dto.AddUserCategory;
import co.kr.jurumarble.user.dto.AddUserInfo;
import co.kr.jurumarble.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserManager {
    private final UserRepository userRepository;

    public void addUserInfo(Long userId, AddUserInfo addUserInfo) {
        userRepository.addUserInfo(userId, addUserInfo);
    }

    public void addUserCategory(Long userId, AddUserCategory addUserCategory) {
        userRepository.addCategory(userId, addUserCategory);
    }
}