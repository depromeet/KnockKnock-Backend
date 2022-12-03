package io.github.depromeet.knockknockbackend.domain.group.domain.repository;

import io.github.depromeet.knockknockbackend.domain.group.domain.Category;
import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.group.domain.GroupType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findAllByGroupType(GroupType groupType);

    List<Group> findAllByCategory(Category category);



    List<Group> findAllByGroupTypeAndCategory(GroupType groupType,Category category);

    List<Group> findByGroupTypeAndTitleContaining(GroupType groupType,String searchString);
}
