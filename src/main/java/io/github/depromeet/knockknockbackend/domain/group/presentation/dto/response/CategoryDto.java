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

    @Schema(description = "카테고리 고유 아이디")
    private Long id;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.emoji = category.getEmoji();
        this.content = category.getContent();
    }
}
