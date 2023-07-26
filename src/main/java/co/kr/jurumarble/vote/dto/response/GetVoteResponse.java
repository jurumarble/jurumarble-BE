package co.kr.jurumarble.vote.dto.response;

import co.kr.jurumarble.enums.AgeType;
import co.kr.jurumarble.enums.GenderType;
import co.kr.jurumarble.enums.MbtiType;
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

    private GenderType filteredGender;

    private AgeType filteredAge;

    private MbtiType filteredMbti;

    private String titleA;

    private String titleB;

    private String description;

    @Builder
    public GetVoteResponse(GetVoteUserResponse writer, LocalDateTime voteCreatedDate, String title, String imageA, String imageB, GenderType filteredGender, AgeType filteredAge, MbtiType filteredMbti, String titleA, String titleB, String description) {
        this.writer = writer;
        this.voteCreatedDate = voteCreatedDate;
        this.title = title;
        this.imageA = imageA;
        this.imageB = imageB;
        this.filteredGender = filteredGender;
        this.filteredAge = filteredAge;
        this.filteredMbti = filteredMbti;
        this.titleA = titleA;
        this.titleB = titleB;
        this.description = description;
    }
}