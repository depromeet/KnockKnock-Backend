package io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response;


import io.github.depromeet.knockknockbackend.domain.group.domain.GroupType;
import io.github.depromeet.knockknockbackend.domain.group.domain.vo.GroupBaseInfoVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupBriefInfoDto {
    private Long groupId;

    private String title;

    private String description;

    private String thumbnailPath;

    @Schema(description = "공개 그룹 여부 ture 면 공개임")
    private Boolean publicAccess;

    @Schema(description = "카테고리 디티오")
    private CategoryDto category;

    @Schema(description = "멤버들 숫자")
    private Integer memberCount;

    private GroupType groupType;

    public GroupBriefInfoDto(GroupBaseInfoVo groupBaseInfoVo) {
        groupId = groupBaseInfoVo.getGroupId();
        title = groupBaseInfoVo.getTitle();
        description = groupBaseInfoVo.getDescription();
        publicAccess = groupBaseInfoVo.getPublicAccess();
        thumbnailPath = groupBaseInfoVo.getThumbnailPath();
        groupType = groupBaseInfoVo.getGroupType();
    }

    public GroupBriefInfoDto(GroupBaseInfoVo groupBaseInfoVo, Integer memberCount) {
        groupId = groupBaseInfoVo.getGroupId();
        title = groupBaseInfoVo.getTitle();
        description = groupBaseInfoVo.getDescription();
        publicAccess = groupBaseInfoVo.getPublicAccess();
        thumbnailPath = groupBaseInfoVo.getThumbnailPath();
        groupType = groupBaseInfoVo.getGroupType();
        this.memberCount = memberCount;
        category = new CategoryDto(groupBaseInfoVo.getCategory());
    }
}
