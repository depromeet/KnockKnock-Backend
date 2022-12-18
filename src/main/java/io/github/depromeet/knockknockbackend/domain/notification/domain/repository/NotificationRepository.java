package io.github.depromeet.knockknockbackend.domain.notification.domain.repository;


import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository
        extends JpaRepository<Notification, Long>, CustomNotificationRepository {

    @EntityGraph(attributePaths = {"group"})
    Slice<Notification> findAllByGroupIdAndDeleted(
            Long groupId, boolean deleted, Pageable pageable);

    @EntityGraph(attributePaths = {"group"})
    Optional<Notification> findById(Long notificationId);
}
