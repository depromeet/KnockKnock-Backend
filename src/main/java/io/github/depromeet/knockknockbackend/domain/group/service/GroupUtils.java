package io.github.depromeet.knockknockbackend.domain.group.service;

import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;

public interface GroupUtils {
    Group queryGroup(Long groupId);

    void addMemberToGroup(Group group, User newUser);
}
