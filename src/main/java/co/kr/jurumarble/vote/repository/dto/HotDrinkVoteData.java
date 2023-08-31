package co.kr.jurumarble.vote.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter // QueryDsl 때문에 필요함
@NoArgsConstructor
@AllArgsConstructor
public class HotDrinkVoteData {

    private Long voteId;
    private String voteTitle;
    private String drinkAImage;
    private String drinkBImage;
}
