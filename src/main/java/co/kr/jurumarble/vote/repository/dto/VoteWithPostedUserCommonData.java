package co.kr.jurumarble.vote.repository.dto;

import co.kr.jurumarble.user.enums.AgeType;
import co.kr.jurumarble.user.enums.AlcoholLimitType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import co.kr.jurumarble.vote.enums.VoteType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteWithPostedUserCommonData {
    private Long voteId;
    private Long postedUserId;
    private String title;
    private String detail;
    private GenderType filteredGender;
    private AgeType filteredAge;
    private MbtiType filteredMbti;
    private Long votedCount;
    private VoteType voteType;
    private LocalDateTime createdAt;
    private String postedUserName;
    private GenderType postedUserGender;
    private Integer postedUserAge;
    private MbtiType postedUserMbti;
    private AlcoholLimitType postedUserAlcoholLimit;
    private String postedUserNickname;
    private String postedUserImageUrl;
}
