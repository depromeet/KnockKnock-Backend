package io.github.depromeet.knockknockbackend.domain.relation.presentation.dto.request;


import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendRequest {

    @NotNull private Long userId;
}
