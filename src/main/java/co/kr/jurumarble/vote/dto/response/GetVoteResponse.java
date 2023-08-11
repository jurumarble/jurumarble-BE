package co.kr.jurumarble.vote.dto.response;

import co.kr.jurumarble.vote.service.GetVoteData;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GetVoteResponse {

    private GetVoteUserResponse writer;

    private LocalDateTime voteCreatedDate;

    private String title;

    private String imageA;

    private String imageB;

    private String titleA;

    private String titleB;

    private String description;


    public GetVoteResponse(GetVoteData data) {
        this.writer = data.getWriter();
        this.voteCreatedDate = data.getVoteCreatedDate();
        this.title = data.getTitle();
        this.imageA = data.getImageA();
        this.imageB = data.getImageB();
        this.titleA = data.getTitleA();
        this.titleB = data.getTitleB();
        this.description = data.getDescription();
    }
}