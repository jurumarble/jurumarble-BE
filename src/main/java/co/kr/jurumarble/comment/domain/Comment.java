package co.kr.jurumarble.comment.domain;

import co.kr.jurumarble.comment.dto.request.CommentCreateRequest;
import co.kr.jurumarble.comment.dto.request.CommentUpdateRequest;
import co.kr.jurumarble.comment.enums.Emotion;
import co.kr.jurumarble.common.domain.BaseTimeEntity;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.enums.AgeType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column
    private Long voteId;
    @Column
    private String content;

    @Enumerated(EnumType.STRING)
    @Column
    private AgeType age;

    @Enumerated(EnumType.STRING)
    @Column
    private MbtiType mbti;

    @Enumerated(EnumType.STRING)
    @Column
    private GenderType gender;

    @Column
    private Integer likeCount;

    @Column
    private Integer hateCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment")
    private List<CommentEmotion> commentEmotionList = new ArrayList<>();


    public Comment(CommentCreateRequest request, Comment parent, User user, Long voteId) {
        this.user = user;
        this.voteId = voteId;
        this.content = request.getContent();
        this.age = user.classifyAge(user.getAge());
        this.mbti = user.getMbti();
        this.gender = user.getGender();
        this.parent = parent;
        this.likeCount = 0;
        this.hateCount = 0;
    }

    public void updateParent(Comment parent) {
        this.parent = parent;
    }

    public void mappingCommentEmotion(CommentEmotion commentEmotion) {
        this.commentEmotionList.add(commentEmotion);
    }

    public void updateLikeHateCount() {
        this.likeCount = (int) commentEmotionList.stream()
                .filter(commentEmotion -> commentEmotion.getEmotion().equals(Emotion.LIKE))
                .count();

        this.hateCount = (int) commentEmotionList.stream()
                .filter(commentEmotion -> commentEmotion.getEmotion().equals(Emotion.HATE))
                .count();
    }

    public void updateContent(CommentUpdateRequest commentUpdateRequest) {
        this.content = commentUpdateRequest.getContent();
    }

    public void removeEmotion(CommentEmotion commentEmotion) {
        this.commentEmotionList.remove(commentEmotion);
    }


}
