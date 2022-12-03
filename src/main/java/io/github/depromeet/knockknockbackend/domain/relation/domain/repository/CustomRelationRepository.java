package io.github.depromeet.knockknockbackend.domain.relation.domain.repository;

import io.github.depromeet.knockknockbackend.domain.relation.domain.Relation;

import java.util.List;
import java.util.Optional;

public interface CustomRelationRepository {
    List<Relation> findFriendList(Long userId);

    Optional<Relation> isAlreadyRequested(Long currentUserId, Long sendUserId);

    Optional<Relation> isPendingRequest(Long currentUserId, Long sendUserId);

    boolean isFriend(Long currentUserId, Long userId);
}
