package io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateCategoryRequest {

    String emoji;
    String content;

}
