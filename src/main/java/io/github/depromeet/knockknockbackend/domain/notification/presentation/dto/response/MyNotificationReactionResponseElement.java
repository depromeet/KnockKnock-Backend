package io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyNotificationReactionResponseElement {
    private final Long notificationReactionId;
    private final Long reactionId;
}
