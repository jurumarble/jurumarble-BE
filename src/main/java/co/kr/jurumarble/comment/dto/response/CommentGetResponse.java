package co.kr.jurumarble.comment.dto.response;


import co.kr.jurumarble.comment.domain.Comment;
import co.kr.jurumarble.user.enums.AgeType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommentGetResponse {

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

    private List<CommentGetResponse> children;

    private Integer likeCount;

    private Integer hateCount;

    @Builder
    public CommentGetResponse(Long id, Long userId, String nickName, Long parentId, String content, String imageUrl, GenderType gender, AgeType age, MbtiType mbti, LocalDateTime createdDate, List<CommentGetResponse> children, Integer likeCount, Integer hateCount) {
        this.id = id;
        this.userId = userId;
        this.nickName = nickName;
        this.parentId = parentId;
        this.content = content;
        this.imageUrl = imageUrl;
        this.gender = gender;
        this.age = age;
        this.mbti = mbti;
        this.createdDate = createdDate;
        this.children = children;
        this.likeCount = likeCount;
        this.hateCount = hateCount;
    }

    @Builder
    public CommentGetResponse(Comment comment) {
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
