package co.kr.jurumarble.vote.domain;

import co.kr.jurumarble.common.domain.BaseTimeEntity;
import co.kr.jurumarble.vote.dto.request.CreateVoteRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class VoteContentEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "VOTE_CONTENT_ID")
    private Long id;

    private String imageA;

    private String imageB;

    private String titleA;

    private String titleB;

    public VoteContentEntity(CreateVoteRequest request) {
        this.imageA = request.getImageA();
        this.imageB = request.getImageB();
        this.titleA = request.getTitleA();
        this.titleB = request.getTitleB();
    }

//    public static VoteContentJpaEntity of(VoteContent voteContent) {
//        return VoteContentJpaEntity.builder()
//                .imageA(voteContent.getImageA())
//                .imageB(voteContent.getImageB())
//                .titleA(voteContent.getTitleA())
//                .titleB(voteContent.getTitleB())
//                .build();
//    }


    public void update(String titleA, String titleB) {

        this.titleA = titleA;
        this.titleB = titleB;

    }
}