package io.github.depromeet.knockknockbackend.domain.report.presentation.dto.response;


import io.github.depromeet.knockknockbackend.domain.report.domain.Report;
import io.github.depromeet.knockknockbackend.domain.report.domain.ReportReason;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReportNotificationResponse {
    private final Long id;
    private final Long notificationId;
    private final ReportReason reportReason;
    private final String description;
    private final Long defendantId;

    public static ReportNotificationResponse from(Report report) {
        return new ReportNotificationResponse(
                report.getId(),
                report.getNotificationId(),
                report.getReportReason(),
                report.getDescription(),
                report.getDefendant().getId());
    }
}
