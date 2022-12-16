package io.github.depromeet.knockknockbackend.domain.notification.domain.repository;

import static io.github.depromeet.knockknockbackend.domain.notification.domain.QNotification.notification;
import static io.github.depromeet.knockknockbackend.domain.storage.domain.QStorage.storage;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CustomNotificationRepositoryImpl implements CustomNotificationRepository {

    private final JPAQueryFactory queryFactory;

    private boolean hasNext(List<Notification> notifications, Pageable pageable) {
        boolean hasNext = false;
        if (notifications.size() > pageable.getPageSize()) {
            notifications.remove(pageable.getPageSize());
            hasNext = true;
        }
        return hasNext;
    }

    @Override
    public Slice<Notification> findSliceFromStorage(Long userId, Long groupId, Pageable pageable) {
        List<Notification> notifications = queryFactory
            .select(notification)
            .from(storage)
            .innerJoin(storage.notification, notification)
            .where(storage.user.id.eq(userId),
                eqGroupId(groupId)
            ).fetch();

        return new SliceImpl<>(notifications, pageable, hasNext(notifications, pageable));
    }

    private BooleanExpression eqGroupId(Long groupId) {
        if(groupId == null) {
            return null;
        }
        return notification.group.id.eq(groupId);
    }

}
