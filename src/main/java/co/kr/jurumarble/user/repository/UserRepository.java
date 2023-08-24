package co.kr.jurumarble.user.repository;

import co.kr.jurumarble.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public boolean existsByProviderId(String providerId);

    public Optional<User> findByProviderId(String providerId);

    public Optional<User> findById(Long userId);

    public Optional<User> findByNickname(String nickname);

    public Optional<User> findByIdAndDeletedDate(Long userId, LocalDateTime deletedAt);

}
