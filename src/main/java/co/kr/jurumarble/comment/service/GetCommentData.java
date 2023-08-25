package co.kr.jurumarble.comment.service;


import co.kr.jurumarble.comment.domain.Comment;
import co.kr.jurumarble.user.enums.AgeType;
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


    @Builder
    public GetCommentData(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getUser().getId();
        this.content = comment.getContent();
        this.gender = comment.getGender();
        this.imageUrl = comment.getUser().getImageUrl();
        this.age = comment.getAge();
        this.mbti = comment.getMbti();
        this.nickName = comment.getUser().getNickname();
        this.createdDate = comment.getCreatedDate();
        this.children = new ArrayList<>();
        this.likeCount = comment.getLikeCount();
        this.hateCount = comment.getHateCount();

        if (comment.getParent() != null) {
            this.parentId = comment.getParent().getId();
        } else {
            this.parentId = null;
        }
    }


}
