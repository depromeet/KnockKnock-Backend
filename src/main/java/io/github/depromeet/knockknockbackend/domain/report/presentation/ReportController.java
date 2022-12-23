package io.github.depromeet.knockknockbackend.domain.report.presentation;


import io.github.depromeet.knockknockbackend.domain.report.presentation.dto.request.ReportNotificationRequest;
import io.github.depromeet.knockknockbackend.domain.report.presentation.dto.response.ReportNotificationResponse;
import io.github.depromeet.knockknockbackend.domain.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "access-token")
@Tag(name = "신고 관련 컨트롤러", description = "")
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
@RestController
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "알림 신고하기", description = "신고타입이 OTHER 일경우, description 을 적어서 보내주세요!")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/notifications/{notification_id}")
    public ReportNotificationResponse reportNotification(
            @RequestBody ReportNotificationRequest reportRequest,
            @PathVariable("notification_id") Long notificationId) {
        return reportService.createReport(notificationId, reportRequest);
    }
}
