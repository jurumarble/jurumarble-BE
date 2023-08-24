package co.kr.jurumarble.vote.service;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateVoteServiceRequest {

    private Long voteId;

    private Long userId;

    private String title;

    private String detail;

    private String titleA;

    private String titleB;


    @Builder
    private UpdateVoteServiceRequest(Long voteId, Long userId, String title, String detail, String titleA, String titleB) {
        this.voteId = voteId;
        this.userId = userId;
        this.title = title;
        this.detail = detail;
        this.titleA = titleA;
        this.titleB = titleB;
    }
}
