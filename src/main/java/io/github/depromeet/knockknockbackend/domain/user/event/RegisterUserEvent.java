package io.github.depromeet.knockknockbackend.domain.user.event;


import io.github.depromeet.knockknockbackend.global.event.DomainEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterUserEvent implements DomainEvent {
    private final Long userId;
}
