package io.github.depromeet.knockknockbackend.domain.relation.domain.repository;

import io.github.depromeet.knockknockbackend.domain.relation.domain.Relation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RelationRepository extends CrudRepository<Relation, Long> {

    @Query(value = "select r from Relation r where r.isFriend = true and (r.sendUser.id = :userId or r.receiveUser.id = :userId)")
    List<Relation> findFriendList(Long userId);

}
