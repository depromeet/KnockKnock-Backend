package io.github.depromeet.knockknockbackend.domain.reaction.domain.repository;

import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import io.github.depromeet.knockknockbackend.domain.notification.domain.vo.NotificationReactionCountInfoVo;
import io.github.depromeet.knockknockbackend.domain.reaction.domain.NotificationReaction;
import java.util.List;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface NotificationReactionRepository extends CrudRepository<NotificationReaction, Long> {

    @Query("select new io.github.depromeet.knockknockbackend.domain.notification.domain.vo.NotificationReactionCountInfoVo(NR.reaction.id , count(NR.reaction.id) ) "
        + "from NotificationReaction  NR "
        + "where NR.notification in :notifications "
        + "group by NR.reaction.id")
    List<NotificationReactionCountInfoVo> findAllCountByNotificationIn(List<Notification> notifications);

    Slice<NotificationReaction> findByUserIdAndNotificationIn(Long userId, List<Notification> notifications);

}
