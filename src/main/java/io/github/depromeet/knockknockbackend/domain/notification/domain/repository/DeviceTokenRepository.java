package io.github.depromeet.knockknockbackend.domain.notification.domain.repository;

import io.github.depromeet.knockknockbackend.domain.notification.domain.DeviceToken;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DeviceTokenRepository extends CrudRepository<DeviceToken, Long> {
    Optional<DeviceToken> findByDeviceId(String deviceId);

    @Query("select DT "
        + "from DeviceToken DT "
        + "where DT.user.id in "
        + "(select GU.user.id "
        + "from GroupUser GU "
        + "join Option O "
        + "on GU.user.id = O.user.id "
        + "where GU.group.id = :groupId "
        + "and O.newOption = :newOption "
        + "AND (coalesce(:nightOption, NULL) IS NULL OR O.nightOption = :nightOption))")
    List<DeviceToken> findUserByGroupIdAndNewOption(@Param("groupId") Long groupId,
        @Param("newOption") Boolean newOption,
        @Param("nightOption") Boolean nightOption);

}
