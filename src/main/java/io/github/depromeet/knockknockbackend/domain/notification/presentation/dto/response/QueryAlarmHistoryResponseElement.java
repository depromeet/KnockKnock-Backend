package io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response;

import io.github.depromeet.knockknockbackend.domain.notification.domain.vo.NotificationBaseInfoVo;
import io.github.depromeet.knockknockbackend.domain.notification.domain.vo.NotificationReactionInfoVo;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder(access = AccessLevel.PRIVATE)
public class QueryAlarmHistoryResponseElement {
    private Long notificationId;
    private String title;
    private String content;
    private String imageUrl;
    private LocalDateTime sendAt;
    private Long sendUserId;
    private NotificationReactionInfoVo reactions;

    public static QueryAlarmHistoryResponseElement from (NotificationBaseInfoVo notificationBaseInfoVo) {
        return QueryAlarmHistoryResponseElement.builder()
            .notificationId(notificationBaseInfoVo.getNotificationId())
            .title(notificationBaseInfoVo.getTitle())
            .content(notificationBaseInfoVo.getContent())
            .imageUrl(notificationBaseInfoVo.getImageUrl())
            .sendAt(notificationBaseInfoVo.getSendAt())
            .sendUserId(notificationBaseInfoVo.getSendUserId())
            .reactions(notificationBaseInfoVo.getNotificationReactionInfoVo())
            .build();
    }

}
