package co.kr.jurumarble.vote.repository.dto;

import lombok.*;

@Getter
@Setter // QueryDsl 때문에 필요함
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteContentData {
    private String imageA;
    private String imageB;
    private String titleA;
    private String titleB;
}
