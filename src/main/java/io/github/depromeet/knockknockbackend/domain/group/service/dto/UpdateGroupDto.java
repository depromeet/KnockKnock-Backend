package io.github.depromeet.knockknockbackend.domain.group.service.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateGroupDto {

    private String title;

    private String description;
    private Boolean publicAccess;

    private String thumbnailPath;

    private String backgroundImagePath;

    private Long categoryId;
}
