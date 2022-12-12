package io.github.depromeet.knockknockbackend.domain.group.domain.repository;

import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.group.domain.GroupType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.query.Param;

public interface CustomGroupRepository {


    Slice<Group> findSliceByGroupType(@Param("groupType") GroupType groupType, PageRequest pageRequest);

}
