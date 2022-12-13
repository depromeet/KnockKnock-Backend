package io.github.depromeet.knockknockbackend.domain.notification.domain.vo;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationBaseInfoVo {
    private final Long notificationId;
    private final String title;
    private final String content;
    private String imageUrl;
    private LocalDateTime sendAt;
    private final Long sendUserId;
    private final NotificationReactionBaseInfoVo notificationReactionBaseInfoVo;
}
