package io.github.depromeet.knockknockbackend.domain.report.service;


import io.github.depromeet.knockknockbackend.domain.notification.domain.Notification;
import io.github.depromeet.knockknockbackend.domain.notification.service.NotificationUtils;
import io.github.depromeet.knockknockbackend.domain.report.domain.Report;
import io.github.depromeet.knockknockbackend.domain.report.domain.ReportReason;
import io.github.depromeet.knockknockbackend.domain.report.domain.repository.ReportRepository;
import io.github.depromeet.knockknockbackend.domain.report.exception.CannotReportMeException;
import io.github.depromeet.knockknockbackend.domain.report.exception.ReportNotFoundException;
import io.github.depromeet.knockknockbackend.domain.report.presentation.dto.request.ReportNotificationRequest;
import io.github.depromeet.knockknockbackend.domain.report.presentation.dto.response.ReportNotificationResponse;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.global.utils.user.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ReportService implements ReportUtils {
    private final ReportRepository reportRepository;

    private final UserUtils userUtils;
    private final NotificationUtils notificationUtils;

    public ReportNotificationResponse createReport(
            Long notificationId, ReportNotificationRequest reportRequest) {
        Notification notification = notificationUtils.queryNotificationById(notificationId);
        User reporter = userUtils.getUserFromSecurityContext();
        Long reporterId = reporter.getId();

        if (notification.getSendUser().getId().equals(reporterId)) {
            throw CannotReportMeException.EXCEPTION;
        }

        // 같은 사람이 같은 알림에 또신고 버튼 눌러도 됨 저장을 안할뿐
        Report report =
                reportRepository
                        .findByReporterIdAndNotificationId(reporterId, notificationId)
                        .orElseGet(
                                () -> {
                                    String description = getDescription(reportRequest);
                                    ReportReason reportReason = reportRequest.getReportReason();

                                    Report newReport =
                                            Report.of(
                                                    reporterId,
                                                    reportReason,
                                                    description,
                                                    notification);
                                    reportRepository.save(newReport);
                                    return newReport;
                                });
        return ReportNotificationResponse.from(report);
    }

    private String getDescription(ReportNotificationRequest reportRequest) {
        String description = reportRequest.getReportReason().getReason();
        if (ReportReason.OTHER.equals(reportRequest.getReportReason())) {
            description = reportRequest.getDescription();
        }
        return description;
    }

    public Report queryReport(Long reportId) {
        return reportRepository
                .findById(reportId)
                .orElseThrow(() -> ReportNotFoundException.EXCEPTION);
    }
}
