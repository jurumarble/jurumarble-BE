package co.kr.jurumarble.comment.service;


import co.kr.jurumarble.comment.domain.Comment;
import co.kr.jurumarble.comment.domain.Restaurant;
import co.kr.jurumarble.user.enums.AgeType;
import co.kr.jurumarble.user.enums.ChoiceType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class GetCommentData {
    private Long id;
    private Long userId;
    private Long voteId;
    private Long drinkId;
    private String nickName;
    private Long parentId;
    private String content;
    private String imageUrl;
    private GenderType gender;
    private AgeType age;
    private MbtiType mbti;
    private LocalDateTime createdDate;
    private List<GetCommentData> children;
    private Integer likeCount;
    private Integer hateCount;
    private ChoiceType choice;
    private Restaurant restaurant;

    @Builder
    public GetCommentData(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getUser().getId();
        this.voteId = comment.getVoteId();
        this.drinkId = comment.getDrinkId();
        this.content = comment.getContent();
        this.gender = comment.getUser().getGender();
        this.imageUrl = comment.getUser().getImageUrl();
        this.age = comment.getUser().classifyAge();
        this.mbti = comment.getUser().getMbti();
        this.nickName = comment.getUser().getNickname();
        this.createdDate = comment.getCreatedDate();
        this.children = new ArrayList<>();
        this.likeCount = comment.getLikeCount();
        this.hateCount = comment.getHateCount();
        this.restaurant = comment.getRestaurant();

        if (comment.getParent() != null) {
            this.parentId = comment.getParent().getId();
        } else {
            this.parentId = null;
        }
    }


    @Builder
    public GetCommentData(Comment comment, ChoiceType choice) {
        this.id = comment.getId();
        this.userId = comment.getUser().getId();
        this.voteId = comment.getVoteId();
        this.drinkId = comment.getDrinkId();
        this.content = comment.getContent();
        this.gender = comment.getUser().getGender();
        this.imageUrl = comment.getUser().getImageUrl();
        this.age = comment.getUser().classifyAge();
        this.mbti = comment.getUser().getMbti();
        this.nickName = comment.getUser().getNickname();
        this.createdDate = comment.getCreatedDate();
        this.children = new ArrayList<>();
        this.likeCount = comment.getLikeCount();
        this.hateCount = comment.getHateCount();
        this.choice = choice;
        this.restaurant = comment.getRestaurant();

        if (comment.getParent() != null) {
            this.parentId = comment.getParent().getId();
        } else {
            this.parentId = null;
        }

        if (choice != null) {
            this.choice = choice;
        } else {
            this.choice = null;
        }
    }
}
