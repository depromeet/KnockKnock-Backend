package io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCategoryRequest {

    String emoji;
    String content;

}
