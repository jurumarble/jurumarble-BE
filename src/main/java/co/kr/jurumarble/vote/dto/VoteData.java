package co.kr.jurumarble.vote.dto;

import co.kr.jurumarble.user.enums.AgeType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import co.kr.jurumarble.vote.enums.VoteType;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class VoteData {

    private Long voteId;
    private Long postedUserId;
    private String title;
    private String detail;
    private GenderType filteredGender;
    private AgeType filteredAge;
    private MbtiType filteredMbti;
    private Long votedCount;
    private VoteType voteType;
    private String imageA;
    private String imageB;
    private String titleA;
    private String titleB;
    private String region;
}
