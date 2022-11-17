package io.github.depromeet.knockknockbackend.domain.user;

import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import java.util.List;

public interface UserUtils {
    User getUserById(Long id);

    List<User> findByIdIn(List<Long> ids);
}
