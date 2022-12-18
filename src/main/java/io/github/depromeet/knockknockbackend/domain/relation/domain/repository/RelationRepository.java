package io.github.depromeet.knockknockbackend.domain.relation.domain.repository;


import io.github.depromeet.knockknockbackend.domain.relation.domain.Relation;
import org.springframework.data.repository.CrudRepository;

public interface RelationRepository
        extends CrudRepository<Relation, Long>, CustomRelationRepository {}
