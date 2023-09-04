package co.kr.jurumarble.vote.dto.request;

import co.kr.jurumarble.vote.service.UpdateDrinkVoteServiceRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateDrinkVoteRequest {

    @Schema(description = "투표 제목", example = "A, B 중 어떤게 나을까요?")
    @NotBlank(message = "투표 제목은 필수입니다.")
    private String title;

    @Schema(description = "전통주 후보 A의 id (전통주 아이디는 다른값으로 넣어주셔야합니다.)")
    @Positive(message = "전통주 아이디는 양수값의 정수여야합니다.")
    private Long drinkAId;

    @Schema(description = "전통주 후보 B의 id (전통주 아이디는 다른값으로 넣어주셔야합니다.)")
    @Positive(message = "전통주 아이디는 양수값의 정수여야합니다.")
    private Long drinkBId;

    public UpdateDrinkVoteServiceRequest toServiceRequest() {
        return UpdateDrinkVoteServiceRequest.builder()
                .title(title)
                .drinkAId(drinkAId)
                .drinkBId(drinkBId)
                .build();
    }
}
