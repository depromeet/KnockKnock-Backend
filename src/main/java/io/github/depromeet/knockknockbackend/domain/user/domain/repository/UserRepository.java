package io.github.depromeet.knockknockbackend.domain.user.domain.repository;

import io.github.depromeet.knockknockbackend.domain.user.domain.User;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByNicknameLike(String nickname);

    Optional<User> findByOauthIdAndOauthProvider(String oauthId , String oauthProvider);
}
