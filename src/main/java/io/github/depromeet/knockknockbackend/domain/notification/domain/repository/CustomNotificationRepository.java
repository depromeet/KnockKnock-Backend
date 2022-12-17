package io.github.depromeet.knockknockbackend.domain.notification.domain.repository;


import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CustomNotificationRepository {

    Slice<Notification> findSliceFromStorage(
            Long userId, Long groupId, Integer periodOfMonth, Pageable pageable);
}
