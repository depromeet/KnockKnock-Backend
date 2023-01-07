package io.github.depromeet.knockknockbackend.domain.alarm.service.dto;


import io.github.depromeet.knockknockbackend.domain.alarm.domain.types.AlarmType;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateAlarmDto {
    private final User sendUser;
    private final User receiveUser;
    private final String title;
    private final String content;
    private final AlarmType type;
}
