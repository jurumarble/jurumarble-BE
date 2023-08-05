package co.kr.jurumarble.vote.domain;


import co.kr.jurumarble.common.domain.BaseTimeEntity;
import co.kr.jurumarble.user.enums.AgeType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import co.kr.jurumarble.vote.dto.request.CreateVoteRequest;
import co.kr.jurumarble.vote.dto.response.GetVoteResponse;
import co.kr.jurumarble.vote.dto.response.GetVoteUserResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static co.kr.jurumarble.vote.domain.QVoteContent.voteContent;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Vote extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "posted_user_id")
    private Long postedUserId;

    private String title;

    private String detail;

    @Enumerated(EnumType.STRING)
    @Column(name = "filtered_gender")
    private GenderType filteredGender;

    @Enumerated(EnumType.STRING)
    @Column(name = "filtered_age")
    private AgeType filteredAge;

    @Enumerated(EnumType.STRING)
    @Column(name = "filtered_mbti")
    private MbtiType filteredMbti;

    public Vote(CreateVoteRequest request, Long postedUserId) {
        this.postedUserId = postedUserId;
        this.title = request.getTitle();
    }

//    public GetVoteResponse toDto(User user) {
//
//        GetVoteUserResponse getVoteUserResponse = GetVoteUserResponse.builder()
//                .build();
//
//        return GetVoteResponse.builder()
//                .writer(getVoteUserResponse)
//                .voteCreatedDate(getCreatedDate())
//                .title(title)
//                .description(detail)
//                .build();
//
//    }

//    public boolean isVoteOfUser(Long userId) {
//        return this.postedUserId.equals(userId);
//    }

//    public void update(UpdateVoteRequest request) {
//        this.title = request.getTitle();
//        this.detail = request.getDetail();
//        this.getVoteContent().update(request.getTitleA(), request.getTitleB());
//    }


}