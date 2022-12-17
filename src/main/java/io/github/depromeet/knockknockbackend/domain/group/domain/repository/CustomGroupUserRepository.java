package io.github.depromeet.knockknockbackend.domain.group.domain.repository;


import io.github.depromeet.knockknockbackend.domain.group.domain.GroupType;
import io.github.depromeet.knockknockbackend.domain.group.domain.GroupUser;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CustomGroupUserRepository {

    Slice<GroupUser> findJoinedGroupUser(Long reqUserId, Pageable pageable);

    Slice<GroupUser> findJoinedGroupUserByGroupType(
            Long reqUserId, GroupType groupType, Pageable pageable);

    Optional<GroupUser> findByGroupIdAndUserId(Long groupId, Long userId);
}
