package io.github.depromeet.knockknockbackend.domain.group.domain.repository;


import io.github.depromeet.knockknockbackend.domain.group.domain.InviteTokenRedisEntity;
import org.springframework.data.repository.CrudRepository;

public interface InviteTokenRedisEntityRepository
        extends CrudRepository<InviteTokenRedisEntity, String> {}
