package io.github.depromeet.knockknockbackend.domain.group.presentation.dto.response;

import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.UserProfileDto;
import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreateOpenGroupResponse {

    private Long groupId ;

    private String title;

    private String description;

    private String thumbnailPath;

    private String backgroundImagePath;

    private Boolean publicAccess;

    private Boolean iHost;

    private GroupCategoryDto groupCategoryDto;

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

        if(group.getGroupCategory() != null)
            groupCategoryDto = new GroupCategoryDto(group.getGroupCategory());
    }

}
