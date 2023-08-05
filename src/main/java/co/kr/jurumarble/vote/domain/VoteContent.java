package co.kr.jurumarble.vote.domain;

import lombok.AccessLevel;
import lombok.Builder;
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

    @Builder
    private VoteContent(Long id, String imageA, String imageB, String titleA, String titleB, Long voteId) {
        this.id = id;
        this.imageA = imageA;
        this.imageB = imageB;
        this.titleA = titleA;
        this.titleB = titleB;
        this.voteId = voteId;
    }

    public void update(String titleA, String titleB) {
        this.titleA = titleA;
        this.titleB = titleB;
    }

    public void mappingVote(Long voteId) {
        this.voteId = voteId;
    }
}