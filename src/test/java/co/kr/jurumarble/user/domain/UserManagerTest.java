package co.kr.jurumarble.user.domain;

import co.kr.jurumarble.exception.user.UserIllegalStateException;
import co.kr.jurumarble.user.enums.MbtiType;
import co.kr.jurumarble.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserManagerTest {

    @InjectMocks
    private UserManager userManager;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("MBTI를 2개월 내에 수정하면 UserIllegalStateException을 반환한다.")
    public void testValidateAndUpdateMbti() {
        // given
        Long userId = 1L;
        LocalDateTime now = LocalDateTime.now();

        User user = User.builder()
                .id(userId)
                .mbti(MbtiType.ENFP)
                .modifiedMbtiDate(now.minusMonths(1))
                .build();
        MbtiType updateMbti = MbtiType.ENFJ;
        // when // then
        assertThrows(UserIllegalStateException.class, () -> userManager.validateAndUpdateMbti(user, updateMbti));
    }

}
