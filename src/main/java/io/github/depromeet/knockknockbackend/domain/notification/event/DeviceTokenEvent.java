package io.github.depromeet.knockknockbackend.domain.notification.event;


import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.global.event.DomainEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeviceTokenEvent implements DomainEvent {

    private final User user;
    //    private final String deviceId or Fcm Token;
}
