package co.kr.jurumarble.vote.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetBookmarkedResponse {

    private boolean bookmarked;


    public GetBookmarkedResponse(boolean bookmarked){
        this.bookmarked = bookmarked;
    }
}