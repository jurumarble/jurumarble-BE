package co.kr.jurumarble.vote.dto;

import co.kr.jurumarble.user.enums.AgeType;
import co.kr.jurumarble.user.enums.GenderType;
import co.kr.jurumarble.user.enums.MbtiType;
import co.kr.jurumarble.vote.domain.VoteContent;
import lombok.*;

@Getter
@Setter // QueryDsl 때문에 필요함
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class FindVoteListData {

    private Long voteId;

    private Long postedUserId;

    private String title;

    private String detail;

    private GenderType filteredGender;

    private AgeType filteredAge;

    private MbtiType filteredMbti;

    private VoteContent voteContentA;

    private VoteContent voteContentB;
}
