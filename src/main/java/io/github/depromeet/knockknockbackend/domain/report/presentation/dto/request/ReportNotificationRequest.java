package io.github.depromeet.knockknockbackend.domain.report.presentation.dto.request;

import io.github.depromeet.knockknockbackend.domain.report.domain.ReportReason;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReportNotificationRequest {

    private final String otherReason;
    private final ReportReason reportReason;
}
