package co.kr.jurumarble.user.domain;

import co.kr.jurumarble.config.JpaAuditionConfig;
import co.kr.jurumarble.exception.user.UserNotFoundException;
import co.kr.jurumarble.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(JpaAuditionConfig.class)
class UserTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("삭제된 유저를 조회해서 생성시 AlreadyDeletedUserException를 throw한다.")
    @Test
    void test() {
        // given
        User user = User.builder()
                .build();

        // when
        user.deleteUser();
        User save = userRepository.save(user);

        // then
        userRepository.findById(save.getId()).orElseThrow(UserNotFoundException::new);

    }

}