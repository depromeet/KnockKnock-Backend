package io.github.depromeet.knockknockbackend.domain.notification.service;


import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import java.time.LocalDateTime;

public interface NotificationUtils {
    Notification queryNotificationById(Long notificationId);

    void sendNotification(
            Long currentUserId,
            Long groupId,
            String title,
            String content,
            String imageUrl,
            LocalDateTime reservedAt);
}
