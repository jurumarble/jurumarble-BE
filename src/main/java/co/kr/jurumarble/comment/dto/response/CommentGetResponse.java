package co.kr.jurumarble.comment.dto.response;


import co.kr.jurumarble.enums.AgeType;
import co.kr.jurumarble.enums.GenderType;
import co.kr.jurumarble.enums.MBTIType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CommentGetResponse {

    private Long id;

    private Long userId;

    private String nickName;

    private Long parentId;

    private String content;

    private String imageUrl;

    private GenderType gender;

    private AgeType age;

    private MBTIType mbti;

    private LocalDateTime createdDate;

    private List<CommentGetResponse> children;

    @Builder
    public CommentGetResponse(Long id, Long userId, String nickName, Long parentId, String content, String imageUrl, GenderType gender, AgeType age, MBTIType mbti, LocalDateTime createdDate, List<CommentGetResponse> children) {
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
    }
}
