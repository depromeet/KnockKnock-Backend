package io.github.depromeet.knockknockbackend.domain.notification.domain.repository;

import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, Long> {

    @EntityGraph(attributePaths = {"group"})
    Slice<Notification> findAllByGroupId(Long groupId, Pageable pageable);


    @EntityGraph(attributePaths = {"group"})
    Optional<Notification> findById(Long notificationId);

}
