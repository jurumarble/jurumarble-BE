package co.kr.jurumarble.vote.domain;

import co.kr.jurumarble.common.domain.BaseTimeEntity;
import co.kr.jurumarble.vote.dto.request.CreateVoteRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "vote_content")
public class VoteContent{

    @Id
    @GeneratedValue
    private Long id;

    private String imageA;

    private String imageB;

    private String titleA;

    private String titleB;

    @Column(name = "vote_id")
    private Long voteId;

    public VoteContent(CreateVoteRequest request) {
        this.imageA = request.getImageA();
        this.imageB = request.getImageB();
        this.titleA = request.getTitleA();
        this.titleB = request.getTitleB();
    }


    public void update(String titleA, String titleB) {
        this.titleA = titleA;
        this.titleB = titleB;
    }
}