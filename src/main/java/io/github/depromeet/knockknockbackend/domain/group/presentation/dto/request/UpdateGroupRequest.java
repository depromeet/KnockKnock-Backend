package io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGroupRequest {
    @NotNull
    private String title;

    @NotNull
    private String description;
    @NotNull
    private Boolean publicAccess;


    private String thumbnailPath;

    private String backgroundImagePath;

    @Schema(defaultValue = "1")
    private Long categoryId;
}
