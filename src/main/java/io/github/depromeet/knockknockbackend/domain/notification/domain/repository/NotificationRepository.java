package io.github.depromeet.knockknockbackend.domain.notification.domain.repository;

import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, Long> {

    Page<Notification> findAllByReceiveUser(User receiveUser, Pageable pageable);

}
