package io.github.depromeet.knockknockbackend.domain.example.domain.repository;


import io.github.depromeet.knockknockbackend.domain.example.domain.RedisEntityExample;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ExampleRedisEntityRepository extends CrudRepository<RedisEntityExample, String> {
    Optional<RedisEntityExample> findByRefreshToken(String refreshToken);
}
