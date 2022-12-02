package io.github.depromeet.knockknockbackend.domain.relation.domain.repository;

import io.github.depromeet.knockknockbackend.domain.relation.domain.Relation;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RelationRepository extends CrudRepository<Relation, Long>, CustomRelationRepository {

}
