package co.kr.jurumarble.comment.dto.request;

import co.kr.jurumarble.comment.enums.SortType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CommentGetRequest {

    @NotNull
    private SortType sortBy;

    @NotNull
    private int page;

    @NotNull
    private int size;
}