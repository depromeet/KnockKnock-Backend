package io.github.depromeet.knockknockbackend.domain.alarm.service;


import io.github.depromeet.knockknockbackend.domain.alarm.service.dto.CreateAlarmDto;

public interface CreateAlarmService {
    void createAlarm(CreateAlarmDto createAlarmDto);
}
