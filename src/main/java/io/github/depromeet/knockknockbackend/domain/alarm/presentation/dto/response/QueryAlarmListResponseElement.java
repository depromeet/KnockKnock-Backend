package io.github.depromeet.knockknockbackend.domain.alarm.presentation.dto.response;

import io.github.depromeet.knockknockbackend.domain.alarm.domain.types.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class QueryAlarmListResponseElement {

    private final Long userId;
    private final String userProfile;
    private final String title;
    private final String content;
    private final AlarmType type;
    private final LocalDateTime createdAt;

}
