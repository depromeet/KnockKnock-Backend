package io.github.depromeet.knockknockbackend.domain.reaction.presentation.dto.request;

import lombok.Getter;

@Getter
public class RegisterReactionRequest {

    private Long notificationReactionId;
    private Long notificationId;
    private Long reactionId;

}
