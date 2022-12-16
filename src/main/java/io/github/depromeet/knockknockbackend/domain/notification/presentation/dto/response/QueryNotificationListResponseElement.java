package io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response;

import io.github.depromeet.knockknockbackend.domain.notification.domain.vo.NotificationReactionInfoVo;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class QueryNotificationListResponseElement {
    private Long notificationId;
    private String title;
    private String content;
    private String imageUrl;
    private LocalDateTime sendAt;
    private Long sendUserId;
    private NotificationReactionInfoVo reactions;

}