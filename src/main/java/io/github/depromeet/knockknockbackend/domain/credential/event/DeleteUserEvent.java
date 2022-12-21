package io.github.depromeet.knockknockbackend.domain.credential.event;


import io.github.depromeet.knockknockbackend.global.event.DomainEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteUserEvent implements DomainEvent {
    private final Long userId;
}
