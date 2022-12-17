package io.github.depromeet.knockknockbackend.domain.example.domain.repository;


import io.github.depromeet.knockknockbackend.domain.example.domain.RedisEntityExample;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ExampleRedisEntityRepository extends CrudRepository<RedisEntityExample, String> {
    Optional<RedisEntityExample> findByRefreshToken(String refreshToken);
}
