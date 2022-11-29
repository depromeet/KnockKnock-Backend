package io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CategoryListResponse {


    List<CategoryDto> categoryList;
}
