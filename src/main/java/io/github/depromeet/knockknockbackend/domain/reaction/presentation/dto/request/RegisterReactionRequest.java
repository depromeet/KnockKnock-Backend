package io.github.depromeet.knockknockbackend.domain.reaction.presentation.dto.request;


import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RegisterReactionRequest {
    @NotNull private Long notificationId;
    @NotNull private Long reactionId;
}
