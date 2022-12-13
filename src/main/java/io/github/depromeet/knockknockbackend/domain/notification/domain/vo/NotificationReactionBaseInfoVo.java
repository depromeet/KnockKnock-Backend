package io.github.depromeet.knockknockbackend.domain.notification.domain.vo;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationReactionBaseInfoVo {
    private Long myReactionId;
    private final List<NotificationReactionInfoVo> reactionInfo;
}
