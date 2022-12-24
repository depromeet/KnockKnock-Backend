package io.github.depromeet.knockknockbackend.domain.report.event;


import io.github.depromeet.knockknockbackend.global.event.DomainEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NewReportEvent implements DomainEvent {

    private final Long reportId;
}
