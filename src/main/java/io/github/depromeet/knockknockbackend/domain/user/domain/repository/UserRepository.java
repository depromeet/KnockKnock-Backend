package io.github.depromeet.knockknockbackend.domain.user.domain.repository;

import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
