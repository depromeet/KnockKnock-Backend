package io.github.depromeet.knockknockbackend.domain.relation.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.depromeet.knockknockbackend.domain.relation.domain.Relation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static io.github.depromeet.knockknockbackend.domain.relation.domain.QRelation.relation;

@RequiredArgsConstructor
@Repository
public class CustomRelationRepositoryImpl implements CustomRelationRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Relation> findFriendList(Long userId) {
        return queryFactory.selectFrom(relation)
                .where(
                        relation.isFriend.eq(true).and(
                                relation.sendUser.id.eq(userId).or(
                                        relation.receiveUser.id.eq(userId)
                                )
                        )
                ).fetch();
    }

    @Override
    public Optional<Relation> findRelationBySendUserIdAndReceiveUserId(Long sendUserId, Long receiveUserId) {
        return Optional.of(
                queryFactory.selectFrom(relation)
                        .where(
                                relation.isFriend.eq(false).and(
                                        friendPredicated(sendUserId, receiveUserId)
                                )
                        )
                        .fetchFirst()
        );
    }

    @Override
    public boolean isFriend(Long currentUserId, Long userId) {
        return queryFactory.select(relation.isFriend.coalesce(false))
                .from(relation)
                .where(
                        friendPredicated(currentUserId, userId).or(
                                friendPredicated(userId, currentUserId)
                        )
                ).fetchFirst();
    }

    private BooleanExpression friendPredicated(Long senderUserId, Long receiveUserId) {
        return relation.sendUser.id.eq(senderUserId).and(
                relation.receiveUser.id.eq(receiveUserId)
        );
    }

}
