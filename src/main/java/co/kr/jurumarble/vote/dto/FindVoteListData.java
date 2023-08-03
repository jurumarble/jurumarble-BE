package co.kr.jurumarble.vote.dto;

import co.kr.jurumarble.vote.domain.Vote;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FindVoteListData {

    Vote vote;

    Long cnt;
}
