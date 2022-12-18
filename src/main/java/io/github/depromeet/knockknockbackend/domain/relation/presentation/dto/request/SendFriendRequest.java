package io.github.depromeet.knockknockbackend.domain.relation.presentation.dto.request;


import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SendFriendRequest {

    @NotNull private Long userId;
}
