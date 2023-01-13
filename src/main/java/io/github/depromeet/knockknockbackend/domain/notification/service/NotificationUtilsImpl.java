package io.github.depromeet.knockknockbackend.domain.notification.service;


import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.MessagingErrorCode;
import com.google.firebase.messaging.SendResponse;
import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.notification.domain.DeviceToken;
import io.github.depromeet.knockknockbackend.domain.notification.domain.NightCondition;
import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import io.github.depromeet.knockknockbackend.domain.notification.domain.repository.DeviceTokenRepository;
import io.github.depromeet.knockknockbackend.domain.notification.domain.repository.NotificationRepository;
import io.github.depromeet.knockknockbackend.domain.notification.exception.FcmTokenInvalidException;
import io.github.depromeet.knockknockbackend.domain.notification.exception.NotificationNotFoundException;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.infrastructor.fcm.FcmService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class NotificationUtilsImpl implements NotificationUtils {

    private final FcmService fcmService;
    private final NotificationRepository notificationRepository;
    private final DeviceTokenRepository deviceTokenRepository;

    @Override
    public Notification queryNotificationById(Long notificationId) {
        return notificationRepository
                .findById(notificationId)
                .orElseThrow(() -> NotificationNotFoundException.EXCEPTION);
    }

    @Override
    public void sendNotification(
            Long sendUserId,
            Long groupId,
            String title,
            String content,
            String imageUrl,
            LocalDateTime reservedAt) {

        List<DeviceToken> deviceTokens = getDeviceTokens(groupId, sendUserId);
        List<String> tokens = getFcmTokens(deviceTokens);

        recordNotification(
                deviceTokens,
                title,
                content,
                imageUrl,
                Group.of(groupId),
                User.of(sendUserId),
                reservedAt);

        if (tokens.isEmpty()) {
            return;
        }
        ApiFuture<BatchResponse> batchResponseApiFuture =
                fcmService.sendGroupMessage(tokens, title, content, imageUrl);
        checkFcmResponse(deviceTokens, tokens, batchResponseApiFuture);
    }

    private List<DeviceToken> getDeviceTokens(Long groupId, Long sendUserId) {
        Boolean nightOption = null;
        if (NightCondition.isNight()) {
            nightOption = true;
        }

        return notificationRepository.findTokenByGroupAndOptionAndNonBlock(
                sendUserId, groupId, nightOption);
    }

    private List<String> getFcmTokens(List<DeviceToken> deviceTokens) {
        return deviceTokens.stream().map(DeviceToken::getToken).collect(Collectors.toList());
    }

    private void recordNotification(
            List<DeviceToken> deviceTokens,
            String title,
            String content,
            String imageUrl,
            Group group,
            User sendUser,
            LocalDateTime reservedAt) {
        Notification notification =
                Notification.makeNotificationWithReceivers(
                        deviceTokens, title, content, imageUrl, group, sendUser, reservedAt);
        notificationRepository.save(notification);
    }

    private void checkFcmResponse(
            List<DeviceToken> deviceTokens,
            List<String> tokens,
            ApiFuture<BatchResponse> batchResponseApiFuture) {
        try {
            List<SendResponse> responses = batchResponseApiFuture.get().getResponses();
            IntStream.iterate(0, i -> i + 1)
                    .limit(responses.size())
                    .filter(i -> responses.get(i).getException() != null)
                    .filter(
                            i ->
                                    responses
                                            .get(i)
                                            .getException()
                                            .getMessagingErrorCode()
                                            .equals(MessagingErrorCode.INVALID_ARGUMENT))
                    .forEach(
                            i -> {
                                String errorToken = tokens.get(i);
                                String errorMessage = responses.get(i).getException().getMessage();
                                MessagingErrorCode errorCode =
                                        responses.get(i).getException().getMessagingErrorCode();

                                Optional<DeviceToken> errorDeviceToken =
                                        deviceTokens.stream()
                                                .filter(
                                                        deviceToken ->
                                                                deviceToken
                                                                        .getToken()
                                                                        .equals(errorToken))
                                                .findAny();
                                Long IdOfErrorDeviceToken =
                                        errorDeviceToken.map(DeviceToken::getId).orElse(null);
                                Long userIdOfErrorDeviceToken =
                                        errorDeviceToken.map(DeviceToken::getUserId).orElse(null);
                                deviceTokenRepository.deleteById(IdOfErrorDeviceToken);

                                log.error(
                                        "**[validateFcmToken] errorUserId: {}, message: {}, messagingErrorCode: {}, errorToken: {}",
                                        userIdOfErrorDeviceToken,
                                        errorMessage,
                                        errorCode,
                                        errorToken);
                            });
        } catch (InterruptedException | ExecutionException e) {
            throw FcmTokenInvalidException.EXCEPTION;
        }
    }
}
