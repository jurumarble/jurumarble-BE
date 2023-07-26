package co.kr.jurumarble.user.repository;

import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.dto.AddUserCategory;
import co.kr.jurumarble.user.dto.AddUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public boolean existsByProviderId(String providerId);

    public Optional<User> findByProviderId(String providerId);

    public Optional<User> findById(Long userId);
}
