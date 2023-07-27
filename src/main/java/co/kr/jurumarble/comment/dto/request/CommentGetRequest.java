package co.kr.jurumarble.comment.dto.request;

import co.kr.jurumarble.enums.AgeType;
import co.kr.jurumarble.enums.GenderType;
import co.kr.jurumarble.enums.MBTIType;
import co.kr.jurumarble.enums.SortType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CommentGetRequest {

    @NotNull
    private SortType sortBy;

    @NotNull
    private int page;

    @NotNull
    private int size;
}