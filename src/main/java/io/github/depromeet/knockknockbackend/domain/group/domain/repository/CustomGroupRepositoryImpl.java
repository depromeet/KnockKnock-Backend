package io.github.depromeet.knockknockbackend.domain.group.domain.repository;

import static io.github.depromeet.knockknockbackend.domain.group.domain.QGroup.group;
import static io.github.depromeet.knockknockbackend.domain.notification.domain.QNotification.notification;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.depromeet.knockknockbackend.domain.group.domain.Category;
import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.group.domain.GroupType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CustomGroupRepositoryImpl implements CustomGroupRepository {

    private final JPAQueryFactory queryFactory;

    private boolean hasNext(List<Group> groups, Pageable pageable) {
        boolean hasNext = false;
        if (groups.size() > pageable.getPageSize()) {
            groups.remove(pageable.getPageSize());
            hasNext = true;
        }
        return hasNext;
    }

    private JPAQuery<Group> getGroupWithRecentNotification() {
        return queryFactory
                .selectFrom(group)
                .leftJoin(notification)
                .on(
                        notification.id.eq(
                                JPAExpressions.select(notification.id.max())
                                        .from(notification)
                                        .where(notification.group.id.eq(group.id))
                                        .orderBy(notification.id.desc())));
    }

    @Override
    public Slice<Group> findSliceByGroupType(GroupType groupType, Pageable pageable) {
        List<Group> groups =
                getGroupWithRecentNotification()
                        .where(group.groupType.eq(groupType))
                        .orderBy(notification.sendAt.coalesce(group.createdDate).desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize() + 1)
                        .fetch();

        return new SliceImpl<>(groups, pageable, hasNext(groups, pageable));
    }

    @Override
    public Slice<Group> findSliceByGroupTypeAndCategory(
            GroupType groupType, Category category, Pageable pageable) {
        List<Group> groups =
                getGroupWithRecentNotification()
                        .where(group.groupType.eq(groupType).and(group.category.eq(category)))
                        .orderBy(notification.sendAt.coalesce(group.createdDate).desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize() + 1)
                        .fetch();

        boolean hasNext = hasNext(groups, pageable);

        return new SliceImpl<>(groups, pageable, hasNext);
    }
}
