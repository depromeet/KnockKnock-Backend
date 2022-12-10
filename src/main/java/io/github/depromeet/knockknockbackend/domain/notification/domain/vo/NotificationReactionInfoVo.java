package io.github.depromeet.knockknockbackend.domain.notification.domain.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationReactionInfoVo {

    private final Long reactionId;
    private final int reactionCount;

}
