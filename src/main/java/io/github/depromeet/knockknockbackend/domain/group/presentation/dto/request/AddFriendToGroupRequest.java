package io.github.depromeet.knockknockbackend.domain.group.presentation.dto.request;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class AddFriendToGroupRequest {
    private List<Long> memberIds = new ArrayList<>();

}
