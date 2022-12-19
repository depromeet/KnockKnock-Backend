package io.github.depromeet.knockknockbackend.domain.notification.domain.repository;


import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.notification.domain.Reservation;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    List<Reservation> findByGroupAndSendUserOrderBySendAtAsc(Group group, User sendUser);
}
