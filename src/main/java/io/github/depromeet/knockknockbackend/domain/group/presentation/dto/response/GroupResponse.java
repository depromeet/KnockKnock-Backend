package io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response;

import io.github.depromeet.knockknockbackend.domain.group.domain.GroupType;
import io.github.depromeet.knockknockbackend.domain.group.domain.vo.GroupBaseInfoVo;
import io.github.depromeet.knockknockbackend.domain.user.domain.vo.UserInfoVO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;


@Getter
public class GroupResponse {

    private Long groupId ;

    private String title;

    private String description;

    private String thumbnailPath;

    private String backgroundImagePath;
    @Schema(description = "공개 그룹 여부 ture 면 공개임")
    private Boolean publicAccess;
    @Schema(description = "내가 호스트(방장)인지에 대한 정보")
    private Boolean iHost;
    @Schema(description = "카테고리 디티오")
    private CategoryDto category;
    @Schema(description = "멤버들 목록")
    private List<MemberInfoDto> members ;

    private GroupType groupType;

    public GroupResponse(GroupBaseInfoVo groupBaseInfoVo, List<UserInfoVO> userInfoList, boolean iHost){
        groupId = groupBaseInfoVo.getGroupId();
        title = groupBaseInfoVo.getTitle();
        description = groupBaseInfoVo.getDescription();
        backgroundImagePath = groupBaseInfoVo.getBackgroundImagePath();
        publicAccess = groupBaseInfoVo.getPublicAccess();
        thumbnailPath = groupBaseInfoVo.getThumbnailPath();
        groupType = groupBaseInfoVo.getGroupType();
        this.iHost = iHost;

        members = userInfoList.stream()
            .map(MemberInfoDto::new)
            .collect(Collectors.toList());

        category = new CategoryDto(groupBaseInfoVo.getCategory());

    }

}
