package io.github.depromeet.knockknockbackend.domain.group.domain.repository;


import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.group.domain.GroupUser;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupUserRepository
        extends JpaRepository<GroupUser, Long>, CustomGroupUserRepository {

    Optional<GroupUser> findByGroupAndUser(Group group, User user);
}
