package io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response;

import io.github.depromeet.knockknockbackend.domain.notification.domain.AlarmType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryAlarmHistoryResponseElement {

    private String sendUserNickname;
    private LocalDateTime sendAt;
    private String content;
    private String imageUrl;
    private AlarmType alarmType;

}
