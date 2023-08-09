package co.kr.jurumarble.vote.service.request;

import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.domain.VoteContent;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateNormalVoteServiceRequest {
    private String title;
    private String titleA;
    private String titleB;
    private String imageA;
    private String imageB;

    @Builder
    private CreateNormalVoteServiceRequest(String title, String titleA, String titleB, String imageA, String imageB) {
        this.title = title;
        this.titleA = titleA;
        this.titleB = titleB;
        this.imageA = imageA;
        this.imageB = imageB;
    }

    public VoteContent toVoteContent() {
        return VoteContent.builder()
                .titleA(titleA)
                .titleB(titleB)
                .imageA(imageA)
                .imageB(imageB)
                .build();
    }

    public Vote toVote(Long userId) {
        return Vote.builder()
                .postedUserId(userId)
                .title(title)
                .build();
    }

}
