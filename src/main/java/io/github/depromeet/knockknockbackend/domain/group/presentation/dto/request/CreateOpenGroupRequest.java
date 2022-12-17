package io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOpenGroupRequest {


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

    @NotNull
    @Positive
    @Schema(defaultValue = "1" , description = "카테고리 디폴트 값은 1 입니다. 비어있는 카테고리 값")
    private Long categoryId;

    @NotNull
    private List<Long> memberIds ;

}
