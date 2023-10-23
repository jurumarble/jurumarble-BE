package co.kr.jurumarble.notification.domain;

import co.kr.jurumarble.common.domain.BaseTimeEntity;
import co.kr.jurumarble.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notification")
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String url;
    @Column(nullable = false, name = "is_read")
    private Boolean isRead;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "notification_type")
    private NotificationType notificationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id")
    private User receiver;

    @Builder
    public Notification(User receiver, NotificationType notificationType, String title, String content, String url, Boolean isRead) {
        this.receiver = receiver;
        this.notificationType = notificationType;
        this.title = title;
        this.content = content;
        this.url = url;
        this.isRead = isRead;
    }

    public enum NotificationType {
        VOTE, COMMENT, ADMIN_NOTIFY
    }

    public void setIsReadTrue() {
        isRead = true;
    }
}