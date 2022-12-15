package io.github.depromeet.knockknockbackend.domain.storage.service;


import io.github.depromeet.knockknockbackend.domain.group.domain.repository.GroupUserRepository;
import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import io.github.depromeet.knockknockbackend.domain.notification.domain.repository.NotificationRepository;
import io.github.depromeet.knockknockbackend.domain.notification.exception.NotificationForbiddenException;
import io.github.depromeet.knockknockbackend.domain.notification.exception.NotificationNotFoundException;
import io.github.depromeet.knockknockbackend.domain.storage.domain.Storage;
import io.github.depromeet.knockknockbackend.domain.storage.repository.StorageRepository;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StorageService {

    private final StorageRepository storageRepository;
    private final NotificationRepository notificationRepository;
    private final GroupUserRepository groupUserRepository;

    public void saveNotificationToStorage(Long notificationId) {
        validateAccessibleNotificationId(notificationId);
        storageRepository.save(
            Storage.of(
                Notification.of(notificationId), User.of(SecurityUtils.getCurrentUserId())
            )
        );
    }

    private void validateAccessibleNotificationId(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> NotificationNotFoundException.EXCEPTION);

        Long groupId = notification.getGroup().getId();
        if (!notification.getGroup().getPublicAccess()) {
            groupUserRepository.findByGroupIdAndUserId(groupId, SecurityUtils.getCurrentUserId())
                .orElseThrow(() -> NotificationForbiddenException.EXCEPTION);
        }
    }
}
