package co.kr.jurumarble.user.dto.request;

import co.kr.jurumarble.user.dto.AddUserCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddCategoryRequest {

    @NotNull(message = "categories는 null 일 수 없습니다.")
    private List<String> categories;

    public AddUserCategory toAddUserCategory() {
        return new AddUserCategory(categories);
    }

}
