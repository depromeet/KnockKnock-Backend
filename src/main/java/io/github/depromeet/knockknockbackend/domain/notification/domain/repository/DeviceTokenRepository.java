package io.github.depromeet.knockknockbackend.domain.notification.domain.repository;

import io.github.depromeet.knockknockbackend.domain.notification.domain.DeviceToken;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface DeviceTokenRepository extends CrudRepository<DeviceToken, Long> {
    Optional<DeviceToken> findByDeviceId(String deviceId);

}
