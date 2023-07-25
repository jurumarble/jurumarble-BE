package co.kr.jurumarble.user.repository;

import co.kr.jurumarble.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
