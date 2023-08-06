package co.kr.jurumarble.vote.dto.response;

import lombok.Builder;
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

    @Builder
    public GetVoteResponse(GetVoteUserResponse writer, LocalDateTime voteCreatedDate, String title, String imageA, String imageB, String titleA, String titleB, String description) {
        this.writer = writer;
        this.voteCreatedDate = voteCreatedDate;
        this.title = title;
        this.imageA = imageA;
        this.imageB = imageB;
        this.titleA = titleA;
        this.titleB = titleB;
        this.description = description;
    }
}