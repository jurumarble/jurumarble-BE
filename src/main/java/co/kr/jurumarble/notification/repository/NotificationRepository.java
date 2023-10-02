package co.kr.jurumarble.notification.repository;

import co.kr.jurumarble.notification.domain.Notification;
import co.kr.jurumarble.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.receiver = :user ORDER BY n.isRead ASC, n.createdDate DESC")
    List<Notification> findNotificationsByUser(@Param("user") User user);
}
