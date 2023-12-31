package co.kr.jurumarble.vote.dto;

import co.kr.jurumarble.user.enums.AgeType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import co.kr.jurumarble.vote.domain.VoteContent;
import co.kr.jurumarble.vote.domain.VoteDrinkContent;
import co.kr.jurumarble.vote.enums.VoteType;
import co.kr.jurumarble.vote.repository.dto.VoteCommonData;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private Long drinkAId;
    private Long drinkBId;
    private String region;
    private LocalDateTime createdAt;

    public static VoteData generateNormalVoteData(VoteCommonData voteCommonData, VoteContent voteContent) {
        return VoteData.builder()
                .voteId(voteCommonData.getVoteId())
                .postedUserId(voteCommonData.getPostedUserId())
                .title(voteCommonData.getTitle())
                .detail(voteCommonData.getDetail())
                .filteredGender(voteCommonData.getFilteredGender())
                .filteredAge(voteCommonData.getFilteredAge())
                .filteredMbti(voteCommonData.getFilteredMbti())
                .imageA(voteContent.getImageA())
                .imageB(voteContent.getImageB())
                .titleA(voteContent.getTitleA())
                .titleB(voteContent.getTitleB())
                .drinkAId(null)
                .drinkBId(null)
                .voteType(voteCommonData.getVoteType())
                .votedCount(voteCommonData.getVotedCount())
                .createdAt(voteCommonData.getCreatedAt())
                .build();
    }


    public static VoteData generateDrinkVoteData(VoteCommonData voteCommonData, VoteDrinkContent voteDrinkContent) {
        return VoteData.builder()
                .voteId(voteCommonData.getVoteId())
                .postedUserId(voteCommonData.getPostedUserId())
                .title(voteCommonData.getTitle())
                .detail(voteCommonData.getDetail())
                .filteredGender(voteCommonData.getFilteredGender())
                .filteredAge(voteCommonData.getFilteredAge())
                .filteredMbti(voteCommonData.getFilteredMbti())
                .imageA(voteDrinkContent.getDrinkAImage())
                .imageB(voteDrinkContent.getDrinkBImage())
                .titleA(voteDrinkContent.getDrinkAName())
                .titleB(voteDrinkContent.getDrinkBName())
                .drinkAId(voteDrinkContent.getDrinkAId())
                .drinkBId(voteDrinkContent.getDrinkBId())
                .votedCount(voteCommonData.getVotedCount())
                .voteType(voteCommonData.getVoteType())
                .region(voteDrinkContent.getRegion())
                .createdAt(voteCommonData.getCreatedAt())
                .build();
    }
}
