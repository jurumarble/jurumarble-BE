package co.kr.jurumarble.comment.domain;

import co.kr.jurumarble.comment.enums.Emotion;
import co.kr.jurumarble.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CommentEmotion {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

    @Column
    @Enumerated(EnumType.STRING)
    private Emotion emotion;

    @Builder
    public CommentEmotion(User user, Comment comment, Emotion emotion) {
        this.user = user;
        this.comment = comment;
        this.emotion = emotion;
    }

    public void mappingComment(Comment comment) {
        this.comment = comment;
        comment.mappingCommentEmotion(this);
    }

    public void mappingUser(User user) {
        this.user = user;
    }

    public void setEmote(Emotion emotion) {
        this.emotion = emotion;
    }

}
