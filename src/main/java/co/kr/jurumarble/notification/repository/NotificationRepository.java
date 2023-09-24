package co.kr.jurumarble.notification.repository;

import co.kr.jurumarble.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
