package io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response;

import io.github.depromeet.knockknockbackend.domain.notification.domain.AlarmType;
import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryAlarmHistoryResponseElement {

    private String sendUserNickname;
    private LocalDateTime sendAt;
    private String content;
    private String imageUrl;
    private AlarmType alarmType;

    public static QueryAlarmHistoryResponseElement from(Notification notification) {
        return QueryAlarmHistoryResponseElement.builder()
            .sendUserNickname(notification.getSendUser().getNickname())
            .sendAt(notification.getSendAt())
            .content(notification.getContent())
            .imageUrl(notification.getImageUrl())
            .alarmType(notification.getAlarmType())
            .build();
    }
}
