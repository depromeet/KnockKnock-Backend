package io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request;


import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateFriendGroupRequest {

    @NotNull private List<Long> memberIds;
}
