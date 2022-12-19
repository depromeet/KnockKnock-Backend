package io.github.depromeet.knockknockbackend.domain.notification.domain.repository;

import static io.github.depromeet.knockknockbackend.domain.group.domain.QGroupUser.groupUser;
import static io.github.depromeet.knockknockbackend.domain.notification.domain.QDeviceToken.deviceToken;
import static io.github.depromeet.knockknockbackend.domain.notification.domain.QNotification.notification;
import static io.github.depromeet.knockknockbackend.domain.notification.domain.QNotificationReceiver.notificationReceiver;
import static io.github.depromeet.knockknockbackend.domain.option.domain.QOption.option;
import static io.github.depromeet.knockknockbackend.domain.relation.domain.QBlockUser.blockUser;
import static io.github.depromeet.knockknockbackend.domain.storage.domain.QStorage.storage;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.depromeet.knockknockbackend.domain.notification.domain.DeviceToken;
import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CustomNotificationRepositoryImpl implements CustomNotificationRepository {

    private static final long NEXT_SLICE_CHECK = 1;
    private static final int NUMBER_OF_LATEST_NOTIFICATIONS = 10;
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
    public Slice<Notification> findSliceFromStorage(
            Long userId, Long groupId, Integer periodOfMonth, Pageable pageable) {
        List<Notification> notifications =
                queryFactory
                        .select(notification)
                        .from(storage)
                        .innerJoin(storage.notification, notification)
                        .where(
                                storage.user.id.eq(userId),
                                eqGroupId(groupId),
                                greaterEqualPeriodOfMonth(periodOfMonth))
                        .orderBy(sort("storage", pageable))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize() + NEXT_SLICE_CHECK)
                        .fetch();

        return new SliceImpl<>(notifications, pageable, hasNext(notifications, pageable));
    }

    @Override
    public List<DeviceToken> findTokenByGroupAndOptionAndNonBlock(
            Long userId, Long groupId, Boolean nightOption) {
        return queryFactory
                .select(deviceToken)
                .from(deviceToken)
                .leftJoin(groupUser)
                .on(deviceToken.user.id.eq(groupUser.user.id))
                .innerJoin(option)
                .on(groupUser.user.id.eq(option.userId))
                .where(
                        groupUser.group.id.eq(groupId),
                        option.newOption.eq(true),
                        eqNightOption(nightOption),
                        JPAExpressions.selectFrom(blockUser)
                                .where(
                                        blockUser.user.eq(groupUser.user),
                                        blockUser.blockedUser.id.eq(userId))
                                .notExists())
                .fetch();
    }

    @Override
    public List<Notification> findSliceLatestByReceiver(Long receiveUserId) {
        return queryFactory
                .selectFrom(notification)
                .where(
                        notification.deleted.eq(false),
                        JPAExpressions.selectFrom(notificationReceiver)
                                .where(
                                        notificationReceiver.notification.id.eq(notification.id),
                                        notificationReceiver.receiver.id.eq(receiveUserId))
                                .exists())
                .orderBy(notification.id.desc())
                .limit(NUMBER_OF_LATEST_NOTIFICATIONS)
                .fetch();
    }

    private BooleanExpression eqNightOption(Boolean nightOption) {
        if (nightOption == null) {
            return null;
        }
        return option.nightOption.eq(nightOption);
    }

    private BooleanExpression eqGroupId(Long groupId) {
        if (groupId == null) {
            return null;
        }
        return notification.group.id.eq(groupId);
    }

    private BooleanExpression greaterEqualPeriodOfMonth(Integer periodOfMonth) {
        if (periodOfMonth == null) {
            return null;
        }
        LocalDate dateCondition = LocalDate.now().minusMonths(periodOfMonth);
        return storage.createdDate.goe(dateCondition.atStartOfDay());
    }

    private OrderSpecifier<?> sort(String domain, Pageable pageable) {
        OrderSpecifier<?> orderSpecifier = null;

        Sort sort = pageable.getSort();
        if (sort.isEmpty()) {
            return null;
        }

        for (Order order : sort) {
            com.querydsl.core.types.Order direction =
                    order.getDirection().isAscending()
                            ? com.querydsl.core.types.Order.ASC
                            : com.querydsl.core.types.Order.DESC;
            orderSpecifier = getOrderSpecifier(domain, orderSpecifier, direction);
        }

        return orderSpecifier;
    }

    private static OrderSpecifier<?> getOrderSpecifier(
            String domain,
            OrderSpecifier<?> orderSpecifier,
            com.querydsl.core.types.Order direction) {
        switch (domain) {
            case "storage":
                orderSpecifier = new OrderSpecifier<>(direction, storage.createdDate);
                break;
            case "notification":
                orderSpecifier = new OrderSpecifier<>(direction, notification.id);
                break;
            default:
                break;
        }
        return orderSpecifier;
    }
}
