package io.github.depromeet.knockknockbackend.domain.group.domain.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.group.domain.GroupType;
import io.github.depromeet.knockknockbackend.domain.notification.domain.QNotification;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import static io.github.depromeet.knockknockbackend.domain.group.domain.QGroup.group;
import static io.github.depromeet.knockknockbackend.domain.notification.domain.QNotification.notification;

@RequiredArgsConstructor
@Repository
public class CustomGroupRepositoryImpl implements CustomGroupRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Group> findSliceByGroupType(@Param("groupType") GroupType groupType, PageRequest pageRequest) {
        List<Group> groups = queryFactory.selectFrom(group)
            .leftJoin(group.notifications, notification)
            .on(notification.id.eq(
                JPAExpressions.select(notification.id.max())
                    .from(notification)
                    .where(notification.group.id.eq(group.id))
                    .orderBy(notification.id.desc())
            ))
            .where(group.groupType.eq(groupType))
            .orderBy(notification.sendAt.coalesce(group.createdDate).desc())
            .offset(pageRequest.getOffset())
            .limit(pageRequest.getPageSize() + 1)
            .fetch();

        boolean hasNext = false;
        if (groups.size() > pageRequest.getPageSize()) {
            groups.remove(pageRequest.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(groups, pageRequest, hasNext);
    };


}
