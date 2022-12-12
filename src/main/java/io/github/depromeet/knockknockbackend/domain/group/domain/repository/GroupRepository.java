package io.github.depromeet.knockknockbackend.domain.group.domain.repository;

import io.github.depromeet.knockknockbackend.domain.group.domain.Category;
import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.group.domain.GroupType;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupRepository extends JpaRepository<Group, Long> ,CustomGroupRepository{


//    @Query(value = "select G.* , N.send_at from tbl_group as G "
//        + "left outer join tbl_notification as N on N.id = "
//        +       "(select id from tbl_notification where group_id = G.id order by id DESC limit 1)"
//        + "where G.group_type = :#{#groupType.getType()} "
//        + "order by coalesce(N.send_at , G.created_date) DESC" , nativeQuery = true)
//    Slice<Group> findSliceByGroupType(@Param("groupType") GroupType groupType, PageRequest pageRequest);

    @Query(value = "select G.* , N.send_at from tbl_group as G "
        + "left outer join tbl_notification as N on N.id = "
        +       "(select id from tbl_notification where group_id = G.id order by id DESC limit 1)"
        + "where G.group_type = :#{#groupType.getType()} and G.category_id = :category "
        + "order by coalesce(N.send_at , G.created_date) DESC" , nativeQuery = true)
    Slice<Group> findSliceByGroupTypeAndCategory(@Param("groupType") GroupType groupType ,@Param("category") Category category , Pageable pageable);

    Slice<Group> findByGroupTypeAndTitleContaining(GroupType groupType,String searchString,PageRequest pageRequest);
}
