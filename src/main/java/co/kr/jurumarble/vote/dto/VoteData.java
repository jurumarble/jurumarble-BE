package co.kr.jurumarble.vote.dto;

import co.kr.jurumarble.user.enums.AgeType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import co.kr.jurumarble.vote.domain.VoteContent;
import co.kr.jurumarble.vote.enums.VoteType;
import co.kr.jurumarble.vote.repository.dto.VoteCommonData;
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

    public static VoteData generateNormalVoteData(VoteCommonData voteCommonData, VoteContent voteContent) {
        return VoteData.builder()
                .voteId(voteCommonData.getVoteId())
                .postedUserId(voteCommonData.getPostedUserId())
                .title(voteCommonData.getTitle())
                .detail(voteCommonData.getDetail())
                .filteredGender(voteCommonData.getFilteredGender())
                .filteredAge(voteCommonData.getFilteredAge())
                .imageA(voteContent.getImageA())
                .imageB(voteContent.getImageB())
                .titleA(voteContent.getTitleA())
                .titleB(voteContent.getTitleB())
                .votedCount(voteCommonData.getVotedCount())
                .build();
    }
}
