package io.github.depromeet.knockknockbackend.domain.group.domain.vo;

import io.github.depromeet.knockknockbackend.domain.group.domain.Category;
import io.github.depromeet.knockknockbackend.domain.group.domain.GroupType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
public class GroupBaseInfoVo {

    private final String title;
    private final String description;
    private final String thumbnailPath;
    private final String backgroundImagePath;
    private final Boolean publicAccess;
    private final Category category;
    private final GroupType groupType;

}
