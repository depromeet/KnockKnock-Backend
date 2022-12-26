package io.github.depromeet.knockknockbackend.domain.storage.service;


import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.group.domain.repository.GroupUserRepository;
import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import io.github.depromeet.knockknockbackend.domain.notification.domain.repository.NotificationRepository;
import io.github.depromeet.knockknockbackend.domain.notification.exception.NotificationForbiddenException;
import io.github.depromeet.knockknockbackend.domain.notification.exception.NotificationNotFoundException;
import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response.QueryNotificationListInStorageResponse;
import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.response.QueryNotificationListResponseElement;
import io.github.depromeet.knockknockbackend.domain.notification.service.NotificationService;
import io.github.depromeet.knockknockbackend.domain.reaction.domain.NotificationReaction;
import io.github.depromeet.knockknockbackend.domain.storage.domain.Storage;
import io.github.depromeet.knockknockbackend.domain.storage.domain.repository.StorageRepository;
import io.github.depromeet.knockknockbackend.domain.storage.exception.StorageForbiddenException;
import io.github.depromeet.knockknockbackend.domain.storage.exception.StorageNotFoundException;
import io.github.depromeet.knockknockbackend.domain.storage.presentation.dto.request.DeleteStorage;
import io.github.depromeet.knockknockbackend.domain.storage.presentation.dto.request.QueryStorageByGroupIds;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    public void deleteNotificationFromStorage(DeleteStorage request) {
        List<Storage> storages = queryStorageByIds(request.getStorageIds());
        validateDeletePermission(storages);
        storageRepository.deleteByIdIn(
                storages.stream().map(Storage::getId).collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    public QueryNotificationListInStorageResponse queryNotificationsInStorage(
            QueryStorageByGroupIds request, Integer periodOfMonth, Pageable pageable) {
        List<Group> groups = new ArrayList<>();
        if (request != null) {
            groups = request.getGroupIds().stream().map(Group::of).collect(Collectors.toList());
        }

        Slice<Notification> notifications =
                notificationRepository.findSliceFromStorage(
                        SecurityUtils.getCurrentUserId(), groups, periodOfMonth, pageable);

        List<NotificationReaction> myNotificationReactions =
                notificationService.retrieveMyReactions(notifications.getContent());
        Slice<QueryNotificationListResponseElement> queryNotificationListResponseElements =
                notifications.map(
                        notification ->
                                notificationService.getQueryNotificationListResponseElements(
                                        notification, myNotificationReactions));

        return new QueryNotificationListInStorageResponse(queryNotificationListResponseElements);
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

    private List<Storage> queryStorageByIds(List<Long> storageIds) {
        List<Storage> storages = storageRepository.findByIdIn(storageIds);
        if (storageIds.size() != storages.size()) {
            throw StorageNotFoundException.EXCEPTION;
        }
        return storages;
    }

    private void validateDeletePermission(List<Storage> storages) {
        storages.forEach(
                storage -> {
                    if (!SecurityUtils.getCurrentUserId().equals(storage.getUser().getId())) {
                        throw StorageForbiddenException.EXCEPTION;
                    }
                });
    }
}
