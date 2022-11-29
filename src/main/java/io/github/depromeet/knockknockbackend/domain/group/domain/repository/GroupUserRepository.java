package io.github.depromeet.knockknockbackend.domain.group.domain.repository;

import io.github.depromeet.knockknockbackend.domain.group.domain.GroupType;
import io.github.depromeet.knockknockbackend.domain.group.domain.GroupUser;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupUserRepository extends JpaRepository<GroupUser, Long> {

    List<GroupUser> findAllByUser(User user);

    @Query("select GU from GroupUser as GU join fetch GU.group where GU.user = :reqUSer and GU.group.groupType = :groupType")
    List<GroupUser> findJoinedGroupUserByGroupType(@Param("reqUser") User reqUser,@Param("groupType") GroupType groupType);
}
