package co.kr.jurumarble.vote.service;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateNormalVoteServiceRequest {

    private final Long voteId;
    private final Long userId;
    private final String title;
    private final String detail;
    private final String titleA;
    private final String titleB;


    @Builder
    private UpdateNormalVoteServiceRequest(Long voteId, Long userId, String title, String detail, String titleA, String titleB) {
        this.voteId = voteId;
        this.userId = userId;
        this.title = title;
        this.detail = detail;
        this.titleA = titleA;
        this.titleB = titleB;
    }
}
