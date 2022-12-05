package io.github.depromeet.knockknockbackend.domain.group.domain.repository;

import io.github.depromeet.knockknockbackend.domain.group.domain.GroupType;
import io.github.depromeet.knockknockbackend.domain.group.domain.GroupUser;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupUserRepository extends JpaRepository<GroupUser, Long> {


    @Query(value = "select GU.* from tbl_group_user as GU "
        + "inner join ("
        +   "select G.* , N.send_at from tbl_group as G left outer join tbl_notification as N on "
        +   "N.id = "
        +       "(select id from tbl_notification where group_id = G.id order by id DESC limit 1)"
        +   ") as joinGroup "
        + "on joinGroup.id = GU.group_id "
        + "where GU.user_id = :reqUserId "
        + "order by coalesce(joinGroup.send_at , joinGroup.created_date) DESC",nativeQuery = true)
    Slice<GroupUser> findJoinedGroupUser(@Param("reqUserId") Long reqUserId, Pageable pageable);

    @Query(value = "select GU.* from tbl_group_user as GU "
        + "inner join ("
        +   "select G.* , N.send_at from tbl_group as G left outer join tbl_notification as N on "
        +   "N.id = "
        +       "(select id from tbl_notification where group_id = G.id order by id DESC limit 1)"
        +   ") as joinGroup "
        + "on joinGroup.id = GU.group_id and joinGroup.group_type = :groupType "
        + "where GU.user_id = :reqUserId "
        + "order by coalesce(joinGroup.send_at , joinGroup.created_date) DESC",nativeQuery = true)
    Slice<GroupUser> findJoinedGroupUserByGroupType(@Param("reqUserId") Long reqUserId,@Param("groupType") GroupType groupType , Pageable pageable);
}
