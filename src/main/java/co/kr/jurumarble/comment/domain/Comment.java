package co.kr.jurumarble.comment.domain;

import co.kr.jurumarble.comment.enums.Emotion;
import co.kr.jurumarble.comment.service.request.UpdateRestaurantServiceRequest;
import co.kr.jurumarble.common.domain.BaseTimeEntity;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.enums.AgeType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment")
public class Comment extends BaseTimeEntity {

    private static final int INITIAL_COUNT = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "vote_id")
    private Long voteId;

    @Column(name = "drink_id")
    private Long drinkId;

    private String content;

    @Enumerated(EnumType.STRING)
    private AgeType age;

    @Enumerated(EnumType.STRING)
    private MbtiType mbti;

    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @Column(name = "like_count")
    private Integer likeCount = INITIAL_COUNT;
    @Column(name = "hate_count")
    private Integer hateCount = INITIAL_COUNT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<CommentEmotion> commentEmotionList = new ArrayList<>();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "restaurantName", column = @Column(name = "restaurant_image")),
            @AttributeOverride(name = "restaurantImage", column = @Column(name = "restaurant_name")),
    })
    private Restaurant restaurant = new Restaurant();

    @Builder
    public Comment(User user, Long voteId, Long drinkId, String content, AgeType age, MbtiType mbti, GenderType gender, Comment parent) {
        this.user = user;
        this.voteId = voteId;
        this.drinkId = drinkId;
        this.content = content;
        this.age = age;
        this.mbti = mbti;
        this.gender = gender;
        this.parent = parent;
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

    public void updateContent(String content) {
        this.content = content;
    }

    public void removeEmotion(CommentEmotion commentEmotion) {
        this.commentEmotionList.remove(commentEmotion);
    }


    public void updateRestaurant(UpdateRestaurantServiceRequest request) {
        if (restaurant == null) {
            restaurant = new Restaurant();
        }
        restaurant.updateRestaurant(request.getRestaurantName(), request.getRestaurantImage());
    }
}
