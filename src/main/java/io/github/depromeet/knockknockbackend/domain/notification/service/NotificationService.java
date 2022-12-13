package io.github.depromeet.knockknockbackend.domain.notification.service;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.notification.domain.DeviceToken;
import io.github.depromeet.knockknockbackend.domain.notification.domain.NightCondition;
import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import io.github.depromeet.knockknockbackend.domain.notification.domain.repository.DeviceTokenRepository;
import io.github.depromeet.knockknockbackend.domain.notification.domain.repository.NotificationRepository;
import io.github.depromeet.knockknockbackend.domain.notification.exception.FcmResponseException;
import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.request.RegisterFcmTokenRequest;
import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.request.SendInstanceRequest;
import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response.QueryAlarmHistoryResponse;
import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response.QueryAlarmHistoryResponseElement;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final DeviceTokenRepository deviceTokenRepository;
    private final EntityManager entityManager;


    @Transactional(readOnly = true)
    public QueryAlarmHistoryResponse queryAlarmHistoryByUserId(Pageable pageable) {
        return null;
    }

    @Transactional(readOnly = true)
    public QueryAlarmHistoryResponse queryHistoryByGroupId(Pageable pageable, Long groupId) {
        Slice<Notification> alarmHistory = notificationRepository.findAllByGroupId(groupId,
            pageable);

        Slice<QueryAlarmHistoryResponseElement> result =
            alarmHistory
                .map(notification -> notification.getNotificationBaseInfoVo(SecurityUtils.getCurrentUserId()))
                .map(QueryAlarmHistoryResponseElement::from);

        return new QueryAlarmHistoryResponse(result);
    }

    @Transactional
    public void registerFcmToken(RegisterFcmTokenRequest request) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Optional<DeviceToken> deviceTokenOptional = deviceTokenRepository.findByDeviceId(
            request.getDeviceId());

        deviceTokenOptional.ifPresentOrElse(
            deviceToken -> {
                if (deviceToken.getUserId().equals(currentUserId)) {
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

    @Transactional
    public void sendInstance(SendInstanceRequest request) {
        Long sendUserId = SecurityUtils.getCurrentUserId();

        List<String> tokens = getTokens(request, sendUserId);
        MulticastMessage multicastMessage = makeMulticastMessageForFcm(request, tokens);

        try {
            BatchResponse batchResponse = FirebaseMessaging.getInstance()
                .sendMulticast(multicastMessage);
            if (batchResponse.getFailureCount() >= 1) {
                handleFcmMessagingException(batchResponse);
            }
        } catch (FirebaseMessagingException e) {
            log.error("[**FCM notification sending Error] {} ", e.getMessage());
            throw FcmResponseException.EXCEPTION;
        }

        notificationRepository.save(
            Notification.of(
                request.getTitle(), request.getContent(), request.getImageUrl(),
                Group.of(request.getGroupId()), User.of(sendUserId), LocalDateTime.now()
            )
        );
    }

    private void handleFcmMessagingException(BatchResponse batchResponse) {
        log.error(
            "[**FCM notification sending Error] successCount : {}, failureCount : {} ",
            batchResponse.getSuccessCount(), batchResponse.getFailureCount()
        );
        batchResponse.getResponses()
            .forEach(
                sendResponse -> log.error(
                    "[**FCM notification sending Error] errorCode: {}, errorMessage : {}",
                    sendResponse.getException().getErrorCode(),
                    sendResponse.getException().getMessage()
                )
            );

        throw FcmResponseException.EXCEPTION;
    }

    private MulticastMessage makeMulticastMessageForFcm(SendInstanceRequest request,
        List<String> tokens) {
        return MulticastMessage.builder()
            .setNotification(
                com.google.firebase.messaging.Notification.builder()
                    .setTitle(request.getTitle())
                    .setBody(request.getContent())
                    .setImage(request.getImageUrl())
                    .build())
            .addAllTokens(tokens)
            .build();
    }

    private List<String> getTokens(SendInstanceRequest request, Long sendUserId) {
        List<DeviceToken> deviceTokens = getDeviceTokensOfGroupUserSettingAlarm(
            request.getGroupId());

        return deviceTokens.stream()
            .filter(deviceToken -> !deviceToken.getUserId().equals(sendUserId))
            .map(DeviceToken::getToken)
            .collect(Collectors.toList());
    }

    private List<DeviceToken> getDeviceTokensOfGroupUserSettingAlarm(Long groupId) {
        Boolean nightOption = null;
        if (NightCondition.isNight()) {
            nightOption = true;
        }

        return deviceTokenRepository.findUserByGroupIdAndNewOption(groupId, true, nightOption);
    }

}
