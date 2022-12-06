package io.github.depromeet.knockknockbackend.domain.notification.service;

import io.github.depromeet.knockknockbackend.domain.notification.domain.DeviceToken;
import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import io.github.depromeet.knockknockbackend.domain.notification.domain.repository.DeviceTokenRepository;
import io.github.depromeet.knockknockbackend.domain.notification.domain.repository.NotificationRepository;
import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.request.RegisterFcmTokenRequest;
import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response.QueryAlarmHistoryResponse;
import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response.QueryAlarmHistoryResponseElement;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final DeviceTokenRepository deviceTokenRepository;
    private final EntityManager entityManager;

    private final NotificationMapper notificationMapper;

    @Transactional
    public QueryAlarmHistoryResponse queryAlarmHistoryByUserId(Pageable pageable) {
        Page<Notification> alarmHistory = notificationRepository.findAllByReceiveUserId(
            SecurityUtils.getCurrentUserId(), pageable);

        List<QueryAlarmHistoryResponseElement> result = alarmHistory.stream()
            .map(notificationMapper::toDtoForQueryAlarmHistory)
            .collect(Collectors.toList());

        return new QueryAlarmHistoryResponse(result);
    }

    @Transactional
    public void registerFcmToken(RegisterFcmTokenRequest request) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Optional<DeviceToken> deviceTokenOptional = deviceTokenRepository.findByDeviceId(
            request.getDeviceId());

        deviceTokenOptional.ifPresentOrElse(
            deviceToken -> {
                if (deviceToken.getUser().getId().equals(currentUserId)) {
                    deviceTokenRepository.save(
                        deviceToken.changeToken(request.getToken()));
                } else {
                    deviceTokenRepository.deleteById(deviceToken.getId());
                    entityManager.flush();
                    deviceTokenRepository.save(
                        DeviceToken.of(currentUserId, request.getDeviceId(), request.getToken()));
                }
            },
            () -> deviceTokenRepository.save(
                DeviceToken.of(currentUserId, request.getDeviceId(), request.getToken()))
        );
    }

}
