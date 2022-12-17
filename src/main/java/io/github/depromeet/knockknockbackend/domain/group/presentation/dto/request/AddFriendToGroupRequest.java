package io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request;


import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddFriendToGroupRequest {
    @NotNull private List<Long> memberIds;
}
