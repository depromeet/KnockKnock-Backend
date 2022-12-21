package io.github.depromeet.knockknockbackend.domain.alarm.service;


import io.github.depromeet.knockknockbackend.domain.alarm.domain.repository.AlarmRepository;
import io.github.depromeet.knockknockbackend.domain.alarm.presentation.dto.response.QueryAlarmListResponse;
import io.github.depromeet.knockknockbackend.domain.alarm.presentation.dto.response.QueryAlarmListResponseElement;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AlarmService {

    private final AlarmRepository alarmRepository;

    public QueryAlarmListResponse queryList() {
        List<QueryAlarmListResponseElement> result =
                alarmRepository
                        .findByReceiveUserIdAndIsActivateIsTrue(SecurityUtils.getCurrentUserId())
                        .stream()
                        .map(
                                alarm ->
                                        new QueryAlarmListResponseElement(
                                                alarm.getSendUserId(),
                                                alarm.getSendUserProfile(),
                                                alarm.getTitle(),
                                                alarm.getContent(),
                                                alarm.getType(),
                                                alarm.getCreatedAt()))
                        .collect(Collectors.toList());

        // TODO 알림 만료처리

        return new QueryAlarmListResponse(result);
    }

    public int queryCount() {
        return alarmRepository
                .findByReceiveUserIdAndIsActivateIsTrue(SecurityUtils.getCurrentUserId())
                .size();
    }
}
