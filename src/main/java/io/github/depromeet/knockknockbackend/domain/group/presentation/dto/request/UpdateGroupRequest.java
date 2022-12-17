package io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request;

import io.github.depromeet.knockknockbackend.domain.group.service.dto.UpdateGroupDto;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateGroupRequest {
    @NotNull
    @Size(min=1,max=18)
    private String title;

    @NotNull
    @Size(min=1,max=80)
    private String description;
    @NotNull
    private Boolean publicAccess;


    private String thumbnailPath;

    private String backgroundImagePath;

    @Schema(defaultValue = "1")
    @NotNull
    @Positive
    private Long categoryId;

    public UpdateGroupDto toUpdateGroupDto(){
        return UpdateGroupDto.builder()
            .backgroundImagePath(backgroundImagePath)
            .categoryId(categoryId)
            .description(description)
            .publicAccess(publicAccess)
            .thumbnailPath(thumbnailPath)
            .title(title)
            .build();
    }
}
