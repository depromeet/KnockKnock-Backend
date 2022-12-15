package io.github.depromeet.knockknockbackend.domain.admission.event;

import io.github.depromeet.knockknockbackend.global.event.DomainEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NewAdmissionEvent implements DomainEvent {
    private final Long requesterId;
    private final Long admissionId;
    private final Long hostId;
    private final Long groupId;
}
