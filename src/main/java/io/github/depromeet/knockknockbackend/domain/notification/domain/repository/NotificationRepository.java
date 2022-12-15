package io.github.depromeet.knockknockbackend.domain.notification.domain.repository;

import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, Long> {

    Page<Notification> findAllByReceiveUserId(Long receiveUserId, Pageable pageable);

    @EntityGraph(attributePaths = {"group"})
    Optional<Notification> findById(Long notificationId);

}
