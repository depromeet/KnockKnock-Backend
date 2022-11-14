package io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response;


import io.github.depromeet.knockknockbackend.domain.group.domain.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CategoryDto {
    @Schema(description = "이모지 정보")
    private String emoji;

    @Schema(description = "안에 내용")
    private String content;

    public CategoryDto(Category category) {
        this.emoji = category.getEmoji();
        this.content = category.getContent();
    }
}
