package io.github.depromeet.knockknockbackend.domain.notification.domain.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationReactionCountInfoVo {

    private final Long reactionId;
    private final int reactionCount;

}
