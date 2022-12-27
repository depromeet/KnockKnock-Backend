package io.github.depromeet.knockknockbackend.domain.reaction.domain.repository;


import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import io.github.depromeet.knockknockbackend.domain.notification.domain.vo.NotificationReactionCountInfoVo;
import io.github.depromeet.knockknockbackend.domain.reaction.domain.NotificationReaction;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface NotificationReactionRepository extends CrudRepository<NotificationReaction, Long> {

    @Query(
            "select new io.github.depromeet.knockknockbackend.domain.notification.domain.vo.NotificationReactionCountInfoVo(NR.notification.id, NR.reaction.id , count(NR.reaction.id) ) "
                    + "from NotificationReaction  NR "
                    + "where NR.notification = :notification "
                    + "group by NR.reaction.id")
    List<NotificationReactionCountInfoVo> findAllCountByNotification(Notification notification);

    List<NotificationReaction> findByUserAndNotificationIn(
            User user, List<Notification> notifications);
}
