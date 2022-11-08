package io.github.depromeet.knockknockbackend.domain.user.domain.repository;

import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {



    Optional<User> findByOauthIdAndOauthProvider(String oauthId , String oauthProvider);
}
