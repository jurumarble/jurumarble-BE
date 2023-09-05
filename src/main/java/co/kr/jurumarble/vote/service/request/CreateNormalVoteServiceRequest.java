package co.kr.jurumarble.vote.service.request;

import co.kr.jurumarble.exception.vote.VoteTypeNotMatchException;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.domain.VoteContent;
import co.kr.jurumarble.vote.enums.VoteType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateNormalVoteServiceRequest {
    private String title;
    private String detail;
    private String titleA;
    private String titleB;
    private String imageA;
    private String imageB;
    private VoteType voteType;

    @Builder
    private CreateNormalVoteServiceRequest(String title, String detail, String titleA, String titleB, String imageA, String imageB, VoteType voteType) {
        validVoteType(voteType);
        this.title = title;
        this.detail = detail;
        this.titleA = titleA;
        this.titleB = titleB;
        this.imageA = imageA;
        this.imageB = imageB;
        this.voteType = voteType;
    }

    private void validVoteType(VoteType voteType) {
        if (voteType != VoteType.NORMAL) {
            throw new VoteTypeNotMatchException();
        }
    }

    public VoteContent toVoteContent() {
        return VoteContent.builder()
                .titleA(titleA)
                .titleB(titleB)
                .imageA(imageA)
                .imageB(imageB)
                .build();
    }

    public Vote toVote(Long userId, String detail) {
        return Vote.builder()
                .postedUserId(userId)
                .voteType(voteType)
                .title(title)
                .detail(detail)
                .build();
    }

}
