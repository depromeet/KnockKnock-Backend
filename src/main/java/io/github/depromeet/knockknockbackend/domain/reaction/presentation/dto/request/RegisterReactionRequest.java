package io.github.depromeet.knockknockbackend.domain.reaction.presentation.dto.request;


import lombok.Getter;
import javax.validation.constraints.NotNull;

@Getter
public class RegisterReactionRequest {
    @NotNull
    private Long notificationId;
    @NotNull
    private Long reactionId;
}
