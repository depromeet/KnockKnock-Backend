package io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOpenGroupRequest {


    @NotNull
    private String title;

    @NotNull
    private String description;
    @NotNull
    private Boolean publicAccess;


    private String thumbnailPath;

    private String backgroundImagePath;

    @NotNull
    @Schema(defaultValue = "1" , description = "건너뛰기일 경우 1로 설정")
    private Long categoryId;

    @NotNull
    private List<Long> memberIds ;

}
