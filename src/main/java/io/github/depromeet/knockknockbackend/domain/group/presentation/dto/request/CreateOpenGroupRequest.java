package io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request;


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

    private Boolean publicAccess;

    private String thumbnailPath;

    private String backgroundImagePath;

    private List<Long> memberIds = new ArrayList<>();

}
