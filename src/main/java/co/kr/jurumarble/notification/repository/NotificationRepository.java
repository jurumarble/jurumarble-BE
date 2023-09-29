package co.kr.jurumarble.notification.repository;

import co.kr.jurumarble.notification.domain.Notification;
import co.kr.jurumarble.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByReceiver(User user);
}
