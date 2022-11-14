package io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response;


import io.github.depromeet.knockknockbackend.domain.group.domain.GroupCategory;
import lombok.Getter;

@Getter
public class GroupCategoryDto {

    private Long categoryId;
    private String emoji;
    private String content;

    public GroupCategoryDto(GroupCategory groupCategory) {
        this.categoryId = groupCategory.getId();
        this.emoji = groupCategory.getEmoji();
        this.content = groupCategory.getContent();
    }
}
