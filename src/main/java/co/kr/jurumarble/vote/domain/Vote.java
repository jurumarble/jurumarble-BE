package co.kr.jurumarble.vote.domain;


import co.kr.jurumarble.common.domain.BaseTimeEntity;
import co.kr.jurumarble.user.enums.AgeType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import co.kr.jurumarble.vote.enums.VoteType;
import co.kr.jurumarble.vote.service.UpdateVoteServiceRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "vote")
public class Vote extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "posted_user_id")
    private Long postedUserId;

    private String title;

    private String detail;

    @Enumerated(EnumType.STRING)
    @Column(name = "vote_type")
    private VoteType voteType;

    @Enumerated(EnumType.STRING)
    @Column(name = "filtered_gender")
    private GenderType filteredGender;

    @Enumerated(EnumType.STRING)
    @Column(name = "filtered_age")
    private AgeType filteredAge;

    @Enumerated(EnumType.STRING)
    @Column(name = "filtered_mbti")
    private MbtiType filteredMbti;

    @Builder
    public Vote(Long postedUserId, String title, String detail, VoteType voteType, GenderType filteredGender, AgeType filteredAge, MbtiType filteredMbti) {
        this.postedUserId = postedUserId;
        this.title = title;
        this.detail = detail;
        this.voteType = voteType;
        this.filteredGender = filteredGender;
        this.filteredAge = filteredAge;
        this.filteredMbti = filteredMbti;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote)) return false;
        Vote vote = (Vote) o;
        return Objects.equals(id, vote.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public boolean isVoteOfUser(Long userId) {
        return this.postedUserId.equals(userId);
    }

    public void update(UpdateVoteServiceRequest request) {
        this.title = request.getTitle();
        this.detail = request.getDetail();
    }


}