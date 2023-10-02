package co.kr.jurumarble.vote.domain;

import co.kr.jurumarble.vote.service.request.UpdateNormalVoteServiceRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "vote_content")
public class VoteContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public void update(UpdateNormalVoteServiceRequest request) {
        this.titleA = request.getTitleA();
        this.titleB = request.getTitleB();
    }

    public void mappingVote(Long voteId) {
        this.voteId = voteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoteContent)) return false;
        VoteContent that = (VoteContent) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}