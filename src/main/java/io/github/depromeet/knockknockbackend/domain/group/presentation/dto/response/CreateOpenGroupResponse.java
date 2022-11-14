package io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response;

import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.UserProfileDto;
import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;


@Getter
public class CreateOpenGroupResponse {

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
    private CategoryDto categoryDto;
    @Schema(description = "멤버들 목록")
    private List<UserProfileDto> members ;


    public CreateOpenGroupResponse(Group group , Boolean iHost ){
        groupId = group.getId();
        title = group.getTitle();
        description = group.getDescription();
        backgroundImagePath = group.getBackgroundImagePath();
        publicAccess = group.getPublicAccess();
        thumbnailPath = group.getThumbnailPath();
        this.iHost = iHost;

        members = group.getMembers().stream()
            .map(member -> new UserProfileDto(member.getUser()))
            .collect(Collectors.toList());

        if(group.getCategory() != null)
            categoryDto = new CategoryDto(group.getCategory());
    }

}
