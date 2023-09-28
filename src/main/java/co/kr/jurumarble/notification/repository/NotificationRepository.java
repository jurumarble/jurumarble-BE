package co.kr.jurumarble.notification.repository;

import co.kr.jurumarble.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
