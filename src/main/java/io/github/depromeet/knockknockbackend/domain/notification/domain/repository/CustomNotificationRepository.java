package io.github.depromeet.knockknockbackend.domain.notification.domain.repository;


import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.notification.domain.DeviceToken;
import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CustomNotificationRepository {

    Slice<Notification> findSliceFromStorage(
            Long userId, List<Group> groups, Integer periodOfMonth, Pageable pageable);

    List<DeviceToken> findTokenByGroupAndOptionAndNonBlock(
            Long userId, Long groupId, Boolean nightOption);

    List<Notification> findSliceLatestByReceiver(Long receiveUserId);

    Slice<Notification> findSliceByGroupId(
            Long userId, Long groupId, boolean deleted, Pageable pageable);
}
