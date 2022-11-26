package io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateGroupRequest {
    @NotNull
    private String title;

    @NotNull
    private String description;
    @NotNull
    private Boolean publicAccess;


    private String thumbnailPath;

    private String backgroundImagePath;

    private Long categoryId;
}
