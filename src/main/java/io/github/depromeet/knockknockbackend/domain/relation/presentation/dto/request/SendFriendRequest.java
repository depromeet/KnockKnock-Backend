package io.github.depromeet.knockknockbackend.domain.relation.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class SendFriendRequest {

    @NotNull
    private Long userId;

}
