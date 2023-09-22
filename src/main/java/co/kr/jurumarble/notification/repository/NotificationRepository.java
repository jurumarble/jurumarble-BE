package co.kr.jurumarble.notification.repository;

import co.kr.jurumarble.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
