package io.github.depromeet.knockknockbackend.domain.notification.domain.repository;

import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, Long> {

    Page<Notification> findAllByReceiveUserId(Long receiveUserId, Pageable pageable);

    Slice<Notification> findAllByGroupId(Long groupId, Pageable pageable);


}
