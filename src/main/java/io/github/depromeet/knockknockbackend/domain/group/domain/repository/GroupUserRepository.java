package io.github.depromeet.knockknockbackend.domain.group.domain.repository;


import io.github.depromeet.knockknockbackend.domain.group.domain.GroupUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupUserRepository
        extends JpaRepository<GroupUser, Long>, CustomGroupUserRepository {}
