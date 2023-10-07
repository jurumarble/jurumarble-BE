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

import java.time.LocalDate;
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
    private Long drinkAId;
    private Long drinkBId;
    private String region;
    private LocalDateTime createdAt;
    private GenderType postedUserGender;
    private AgeType postedUserAge;
    private MbtiType postedUserMbti;
    private AlcoholLimitType postedUserAlcoholLimit;
    private String postedUserNickname;
    private String postedUserImageUrl;

    public static VoteWithPostedUserData generateNormalVoteData(VoteWithPostedUserCommonData voteCommonData, VoteContent voteContent) {
        AgeType classifiedAge = classifyAge(voteCommonData.getPostedUserYearOfBirth());
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
                .drinkAId(null)
                .drinkBId(null)
                .voteType(voteCommonData.getVoteType())
                .votedCount(voteCommonData.getVotedCount())
                .createdAt(voteCommonData.getCreatedAt())
                .postedUserGender(voteCommonData.getPostedUserGender())
                .postedUserAge(classifiedAge)
                .postedUserMbti(voteCommonData.getPostedUserMbti())
                .postedUserAlcoholLimit(voteCommonData.getPostedUserAlcoholLimit())
                .postedUserNickname(voteCommonData.getPostedUserNickname())
                .postedUserImageUrl(voteCommonData.getPostedUserImageUrl())
                .build();
    }

    public static VoteWithPostedUserData generateDrinkVoteData(VoteWithPostedUserCommonData voteCommonData, VoteDrinkContent voteDrinkContent) {
        AgeType classifiedAge = classifyAge(voteCommonData.getPostedUserYearOfBirth());
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
                .drinkAId(voteDrinkContent.getDrinkAId())
                .drinkBId(voteDrinkContent.getDrinkBId())
                .votedCount(voteCommonData.getVotedCount())
                .voteType(voteCommonData.getVoteType())
                .region(voteDrinkContent.getRegion())
                .createdAt(voteCommonData.getCreatedAt())
                .postedUserGender(voteCommonData.getPostedUserGender())
                .postedUserAge(classifiedAge)
                .postedUserMbti(voteCommonData.getPostedUserMbti())
                .postedUserAlcoholLimit(voteCommonData.getPostedUserAlcoholLimit())
                .postedUserNickname(voteCommonData.getPostedUserNickname())
                .postedUserImageUrl(voteCommonData.getPostedUserImageUrl())
                .build();
    }

    public static AgeType classifyAge(Integer yearOfBirth) {
        if (yearOfBirth == null) {
            return null; // 혹은 원하는 다른 동작 수행
        }
        LocalDate localDate = LocalDate.now();
        int age = localDate.getYear() - yearOfBirth + 1;
        AgeType ageGroup;
        switch (age / 10) {
            case 1:
                ageGroup = AgeType.teenager;
                break;
            case 2:
                ageGroup = AgeType.twenties;
                break;
            case 3:
                ageGroup = AgeType.thirties;
                break;
            case 4:
                ageGroup = AgeType.fourties;
                break;
            case 5:
                ageGroup = AgeType.fifties;
                break;
            default:
                ageGroup = null;
                break;
        }
        return ageGroup;
    }
}
