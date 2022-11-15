package io.github.depromeet.knockknockbackend.domain.relation.domain.repository;

import io.github.depromeet.knockknockbackend.domain.relation.domain.Relation;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RelationRepository extends CrudRepository<Relation, Long> {

    @Query(value = "select r from Relation r where r.isFriend = true and (r.sendUser.id = :userId or r.receiveUser.id = :userId)")
    List<Relation> findFriendList(Long userId);

    @Query(value = "select r from Relation r where r.isFriend = false and (r.sendUser.id = :currentUserId and r.receiveUser.id = :sendUserId)")
    Optional<Relation> isAlreadyRequested(Long currentUserId, Long sendUserId); // TODO 추후 QueryDSL로 이관.

    @Query(value = "select r from Relation r where r.isFriend = false and (r.sendUser.id = :sendUserId and r.receiveUser.id = :currentUserId)")
    Optional<Relation> isPendingRequest(Long currentUserId, Long sendUserId); // TODO 추후 QueryDSL로 이관.

}
