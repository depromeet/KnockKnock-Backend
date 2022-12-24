package io.github.depromeet.knockknockbackend.domain.notification.service;


import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;

public interface NotificationUtils {
    Notification queryNotificationById(Long notificationId);
}
