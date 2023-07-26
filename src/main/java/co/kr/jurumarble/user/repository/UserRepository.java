package co.kr.jurumarble.user.repository;

import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.dto.AddUserCategory;
import co.kr.jurumarble.user.dto.AddUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User register(User user);

    public void addUserInfo(Long userId, AddUserInfo addUserInfo);

    public void addCategory(Long userId, AddUserCategory addUserCategory);

    public boolean existsByProviderId(String providerId);

    public User findByProviderId(String providerId);

    public User findByUserId(Long userId);
}
