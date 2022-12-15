package io.github.depromeet.knockknockbackend.domain.group.domain.repository;

import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.group.domain.GroupType;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroupRepository extends JpaRepository<Group, Long> ,CustomGroupRepository{

    Slice<Group> findByGroupTypeAndTitleContaining(GroupType groupType,String searchString,PageRequest pageRequest);

    @Query(value =
                "SELECT g.* ,notiGroup.memberCount + notiGroup.notiCount + notiGroup.reactionCount as score "
              + "FROM ( "
              + "       SELECT noti.group_id, count(*) as notiCount , ( "
              + "           SELECT count(*) "
              + "           FROM knock.tbl_group_user as gu "
              + "           WHERE gu.group_id = noti.group_id "
              + "       ) AS memberCount , sum( "
              + "       ( "
              + "           SELECT count(*) "
              + "           FROM knock.tbl_notification_reaction as nr "
              + "           WHERE nr.notification_id = noti.id "
              + "       ) "
              + "    ) AS reactionCount "
              + "    FROM knock.tbl_notification as noti "
              + "    GROUP BY noti.group_id "
              + ") AS notiGroup , tbl_group as g "
              + "WHERE g.id = notiGroup.group_id "
              + "ORDER BY score DESC "
              + "limit 5" ,nativeQuery = true)
    List<Group> findFamousGroup();
}