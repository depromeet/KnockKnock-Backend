package io.github.depromeet.knockknockbackend.domain.notification.service;


import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.notification.domain.DeviceToken;
import io.github.depromeet.knockknockbackend.domain.notification.domain.NightCondition;
import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import io.github.depromeet.knockknockbackend.domain.notification.domain.repository.NotificationRepository;
import io.github.depromeet.knockknockbackend.domain.notification.exception.NotificationNotFoundException;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.infrastructor.fcm.FcmService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NotificationUtilsImpl implements NotificationUtils {

    private final FcmService fcmService;
    private final NotificationRepository notificationRepository;

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
        fcmService.sendGroupMessage(tokens, title, content, imageUrl);
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
}
