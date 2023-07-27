package co.kr.jurumarble.comment.domain;

import co.kr.jurumarble.comment.dto.request.CommentCreateRequest;
import co.kr.jurumarble.comment.dto.request.CommentUpdateRequest;
import co.kr.jurumarble.common.domain.BaseTimeEntity;
import co.kr.jurumarble.enums.AgeType;
import co.kr.jurumarble.enums.GenderType;
import co.kr.jurumarble.enums.MBTIType;
import co.kr.jurumarble.user.domain.User;
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
    private MBTIType mbti;

    @Enumerated(EnumType.STRING)
    @Column
    private GenderType gender;

    @Column
    private Long likeCount;

    @Column
    private Long hateCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    public Comment(CommentCreateRequest request, Comment parentComment, User user, Long voteId) {
        this.user = user;
        this.voteId = voteId;
        this.parent = parentComment;
        this.content = request.getContent();
        this.age = user.classifyAge(user.getAge());
        this.mbti = user.getMbti();
        this.gender = user.getGender();
    }

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment")
//    private List<CommentEmotion> commentEmotionList = new ArrayList<>();
//
//    public void mappingCommentEmotion(CommentEmotion commentEmotion) {
//        this.commentEmotionList.add(commentEmotion);
//    }

//    public void updateLikeHateCount() {
//        int likecnt = 0;
//        int hatecnt = 0;
//        for (CommentEmotion commentEmotion : commentEmotionList) {
//            if (commentEmotion.getEmotion().equals(Emotion.LIKE)) {
//                likecnt += 1;
//            } else {
//                hatecnt += 1;
//            }
//        }
//        this.likeCount = (long) likecnt;
//        this.hateCount = (long) hatecnt;
//    }

//    public void removeEmotion(CommentEmotion commentEmotion) {
//        this.commentEmotionList.remove(commentEmotion);
//    }

    public void updateContent(CommentUpdateRequest commentUpdateRequest) {
        this.content = commentUpdateRequest.getContent();
    }


}
