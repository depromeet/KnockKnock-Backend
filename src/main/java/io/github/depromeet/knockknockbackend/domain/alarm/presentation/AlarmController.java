package io.github.depromeet.knockknockbackend.domain.alarm.presentation;


import io.github.depromeet.knockknockbackend.domain.alarm.presentation.dto.response.QueryAlarmCountResponse;
import io.github.depromeet.knockknockbackend.domain.alarm.presentation.dto.response.QueryAlarmListResponse;
import io.github.depromeet.knockknockbackend.domain.alarm.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/alarms")
@RestController
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping("/count")
    public QueryAlarmCountResponse queryCount() {
        return new QueryAlarmCountResponse(alarmService.queryCount());
    }

    @GetMapping()
    public QueryAlarmListResponse queryList() {
        return alarmService.queryList();
    }
}
