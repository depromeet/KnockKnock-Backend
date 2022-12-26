package io.github.depromeet.knockknockbackend.domain.notification.domain.repository;


import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.notification.domain.Reservation;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    List<Reservation> findByGroupAndSendUserOrderBySendAtAsc(Group group, User sendUser);

    List<Reservation> findBySendAtLessThan(LocalDateTime sendAt);

    @Modifying
    @Query("delete from Reservation r where r.id in :reservationIds")
    void deleteByIdIn(@Param("reservationIds") List<Long> reservationIds);
}
