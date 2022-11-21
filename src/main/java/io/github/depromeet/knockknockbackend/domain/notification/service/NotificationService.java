package io.github.depromeet.knockknockbackend.domain.notification.service;

import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import io.github.depromeet.knockknockbackend.domain.notification.domain.repository.NotificationRepository;
import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response.QueryAlarmHistoryResponse;
import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response.QueryAlarmHistoryResponseElement;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Transactional
    public QueryAlarmHistoryResponse queryAlarmHistoryByUserId(Pageable pageable) {
        Page<Notification> alarmHistory = notificationRepository.findAllByReceiveUserId(SecurityUtils.getCurrentUserId(), pageable);

        List<QueryAlarmHistoryResponseElement> result = alarmHistory.stream()
            .map(notification -> notificationMapper.toDtoForQueryAlarmHistory(notification))
            .collect(Collectors.toList());

        return new QueryAlarmHistoryResponse(result);
    }
}
