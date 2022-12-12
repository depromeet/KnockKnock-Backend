package io.github.depromeet.knockknockbackend.domain.group.domain.repository;

import static io.github.depromeet.knockknockbackend.domain.group.domain.QGroup.group;
import static io.github.depromeet.knockknockbackend.domain.group.domain.QGroupUser.groupUser;
import static io.github.depromeet.knockknockbackend.domain.notification.domain.QNotification.notification;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.group.domain.GroupType;
import io.github.depromeet.knockknockbackend.domain.group.domain.GroupUser;
import io.github.depromeet.knockknockbackend.domain.group.domain.QGroup;
import io.github.depromeet.knockknockbackend.domain.group.domain.QGroupUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CustomGroupUserRepositoryImpl implements CustomGroupUserRepository{

    private final JPAQueryFactory queryFactory;


    private boolean hasNext(List<GroupUser> groupUsers, Pageable pageable) {
        boolean hasNext = false;
        if (groupUsers.size() > pageable.getPageSize()) {
            groupUsers.remove(pageable.getPageSize());
            hasNext = true;
        }
        return hasNext;
    }

    private JPAQuery<Group> getGroupWithRecentNotification() {
        return queryFactory.selectFrom(group)
            .leftJoin(notification)
            .on(notification.id.eq(
                JPAExpressions.select(notification.id.max())
                    .from(notification)
                    .where(notification.group.id.eq(group.id))
                    .orderBy(notification.id.desc())
            ));
    }

    @Override
    public Slice<GroupUser> findJoinedGroupUser(Long reqUserId, Pageable pageable) {
        List<GroupUser> groupUsers = queryFactory.selectFrom(groupUser)
            .join(groupUser.group, group)
            .fetchJoin()
            .leftJoin(notification)
            .on(notification.id.eq(
                JPAExpressions.select(notification.id.max())
                    .from(notification)
                    .where(notification.group.id.eq(group.id))
                    .orderBy(notification.id.desc())
            ))
            .where(groupUser.user.id.eq(reqUserId))
            .orderBy(notification.sendAt.coalesce(group.createdDate).desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize() + 1)
            .fetch();

        return new SliceImpl<>(groupUsers, pageable, hasNext(groupUsers, pageable));
    }

    @Override
    public Slice<GroupUser> findJoinedGroupUserByGroupType(Long reqUserId, GroupType groupType,
        Pageable pageable) {
        return null;
    }
}
