package co.kr.jurumarble.notification.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CommentCreatedEvent extends ApplicationEvent {
    private final Long voteId;

    public CommentCreatedEvent(Object source, Long voteId) {
        super(source);
        this.voteId = voteId;
    }
}