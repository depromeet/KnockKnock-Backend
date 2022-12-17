package io.github.depromeet.knockknockbackend.domain.notification.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class NotificationReactionCountInfoVo {

    private final Long notificationId;
    private final Long reactionId;
    private final Long reactionCount;
}
