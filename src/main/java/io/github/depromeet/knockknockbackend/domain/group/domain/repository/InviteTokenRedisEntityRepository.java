package io.github.depromeet.knockknockbackend.domain.group.domain.repository;

import io.github.depromeet.knockknockbackend.domain.group.domain.InviteTokenRedisEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface InviteTokenRedisEntityRepository extends CrudRepository<InviteTokenRedisEntity, String> {

    Optional<InviteTokenRedisEntity> findByToken(String token);
}