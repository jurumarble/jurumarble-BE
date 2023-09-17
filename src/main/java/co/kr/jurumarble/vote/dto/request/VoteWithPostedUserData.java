package co.kr.jurumarble.vote.dto.request;

import co.kr.jurumarble.user.enums.AgeType;
import co.kr.jurumarble.user.enums.AlcoholLimitType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import co.kr.jurumarble.vote.domain.VoteContent;
import co.kr.jurumarble.vote.domain.VoteDrinkContent;
import co.kr.jurumarble.vote.enums.VoteType;
import co.kr.jurumarble.vote.repository.dto.VoteWithPostedUserCommonData;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteWithPostedUserData {

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
    private LocalDateTime createdAt;
    private GenderType postedUserGender;
    private Integer postedUserAge;
    private MbtiType postedUserMbti;
    private AlcoholLimitType postedUserAlcoholLimit;
    private String postedUserNickname;
    private String postedUserImageUrl;

    public static VoteWithPostedUserData generateNormalVoteData(VoteWithPostedUserCommonData voteCommonData, VoteContent voteContent) {
        return VoteWithPostedUserData.builder()
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
                .voteType(voteCommonData.getVoteType())
                .votedCount(voteCommonData.getVotedCount())
                .createdAt(voteCommonData.getCreatedAt())
                .postedUserGender(voteCommonData.getPostedUserGender())
                .postedUserAge(voteCommonData.getPostedUserAge())
                .postedUserMbti(voteCommonData.getPostedUserMbti())
                .postedUserAlcoholLimit(voteCommonData.getPostedUserAlcoholLimit())
                .postedUserNickname(voteCommonData.getPostedUserNickname())
                .postedUserImageUrl(voteCommonData.getPostedUserImageUrl())
                .build();
    }

    public static VoteWithPostedUserData generateDrinkVoteData(VoteWithPostedUserCommonData voteCommonData, VoteDrinkContent voteDrinkContent) {
        return VoteWithPostedUserData.builder()
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
                .votedCount(voteCommonData.getVotedCount())
                .voteType(voteCommonData.getVoteType())
                .region(voteDrinkContent.getRegion())
                .createdAt(voteCommonData.getCreatedAt())
                .postedUserGender(voteCommonData.getPostedUserGender())
                .postedUserAge(voteCommonData.getPostedUserAge())
                .postedUserMbti(voteCommonData.getPostedUserMbti())
                .postedUserAlcoholLimit(voteCommonData.getPostedUserAlcoholLimit())
                .postedUserNickname(voteCommonData.getPostedUserNickname())
                .postedUserImageUrl(voteCommonData.getPostedUserImageUrl())
                .build();
    }
}
