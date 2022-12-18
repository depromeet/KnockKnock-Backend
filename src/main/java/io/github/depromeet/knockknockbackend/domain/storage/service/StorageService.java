package io.github.depromeet.knockknockbackend.domain.storage.service;


import io.github.depromeet.knockknockbackend.domain.group.domain.repository.GroupUserRepository;
import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import io.github.depromeet.knockknockbackend.domain.notification.domain.repository.NotificationRepository;
import io.github.depromeet.knockknockbackend.domain.notification.exception.NotificationForbiddenException;
import io.github.depromeet.knockknockbackend.domain.notification.exception.NotificationNotFoundException;
import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response.QueryNotificationListInStorageResponse;
import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response.QueryNotificationListResponseElement;
import io.github.depromeet.knockknockbackend.domain.notification.service.NotificationService;
import io.github.depromeet.knockknockbackend.domain.storage.domain.Storage;
import io.github.depromeet.knockknockbackend.domain.storage.domain.repository.StorageRepository;
import io.github.depromeet.knockknockbackend.domain.storage.exception.StorageForbiddenException;
import io.github.depromeet.knockknockbackend.domain.storage.exception.StorageNotFoundException;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StorageService {

    private final NotificationService notificationService;
    private final StorageRepository storageRepository;
    private final NotificationRepository notificationRepository;
    private final GroupUserRepository groupUserRepository;

    public void saveNotificationToStorage(Long notificationId) {
        validateAccessibleNotificationId(notificationId);
        storageRepository.save(
                Storage.of(
                        Notification.of(notificationId),
                        User.of(SecurityUtils.getCurrentUserId())));
    }

    @Transactional
    public void deleteNotificationFromStorage(Long storageId) {
        Storage storage = queryStorageById(storageId);
        if (!SecurityUtils.getCurrentUserId().equals(storage.getUser().getId())) {
            throw StorageForbiddenException.EXCEPTION;
        }
        storageRepository.delete(storage);
    }

    @Transactional(readOnly = true)
    public QueryNotificationListInStorageResponse queryNotificationsInStorage(
            Long groupId, Integer periodOfMonth, Pageable pageable) {
        Slice<Notification> notifications =
                notificationRepository.findSliceFromStorage(
                        SecurityUtils.getCurrentUserId(), groupId, periodOfMonth, pageable);

        List<QueryNotificationListResponseElement> notificationListResponseElements =
                notificationService.getNotificationListResponseElements(notifications.getContent());

        return new QueryNotificationListInStorageResponse(
                new SliceImpl(
                        notificationListResponseElements,
                        notifications.getPageable(),
                        notifications.hasNext()));
    }

    private void validateAccessibleNotificationId(Long notificationId) {
        Notification notification =
                notificationRepository
                        .findById(notificationId)
                        .orElseThrow(() -> NotificationNotFoundException.EXCEPTION);

        Long groupId = notification.getGroup().getId();
        if (!notification.getGroup().getPublicAccess()) {
            groupUserRepository
                    .findByGroupIdAndUserId(groupId, SecurityUtils.getCurrentUserId())
                    .orElseThrow(() -> NotificationForbiddenException.EXCEPTION);
        }
    }

    private Storage queryStorageById(Long storageId) {
        return storageRepository
                .findById(storageId)
                .orElseThrow(() -> StorageNotFoundException.EXCEPTION);
    }
}
