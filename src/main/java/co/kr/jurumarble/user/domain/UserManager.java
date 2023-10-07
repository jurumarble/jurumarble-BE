package co.kr.jurumarble.user.domain;

import co.kr.jurumarble.exception.user.BirthYearLimitExceededException;
import co.kr.jurumarble.exception.user.UserBirthYearMinorException;
import co.kr.jurumarble.exception.user.UserIllegalStateException;
import co.kr.jurumarble.exception.user.UserNotFoundException;
import co.kr.jurumarble.user.dto.AddUserInfo;
import co.kr.jurumarble.user.dto.UpdateUserInfo;
import co.kr.jurumarble.user.enums.MbtiType;
import co.kr.jurumarble.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserManager {
    private final UserRepository userRepository;

    public void addUserInfo(Long userId, AddUserInfo addUserInfo) {
        User user = userRepository.findByIdAndDeletedDate(userId, null).orElseThrow(UserNotFoundException::new);
        user.addInfo(addUserInfo);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.deleteUser();
    }

    public void updateUser(Long userId, UpdateUserInfo updateUserInfo) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        validateAndUpdateMbti(user, updateUserInfo.getMbti());
        user.updateUser(updateUserInfo);
    }

    public void validateAndUpdateMbti(User user, MbtiType mbti) {
        if (!Objects.equals(mbti, user.getMbti())) {
            LocalDateTime now = LocalDateTime.now();
            if (user.getModifiedMbtiDate() != null && now.isBefore(user.getModifiedMbtiDate().plusMonths(2))) {
                throw new UserIllegalStateException();
            } else {
                user.updateMbti(mbti);
            }
        }
    }

    public void validBirth(Long year) {
        if(year <= 1900) {
            throw new BirthYearLimitExceededException();
        }
        LocalDate now = LocalDate.now();
        int age = (int) (now.getYear() - year + 1);
        if(age < 20) {
            throw new UserBirthYearMinorException();
        }
    }
}