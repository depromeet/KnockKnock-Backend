package io.github.depromeet.knockknockbackend.domain.report.presentation;

import io.github.depromeet.knockknockbackend.domain.report.presentation.dto.request.ReportNotificationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "알림 신고하기")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/notifications/{notification_id}")
    public void reportNotification(
    @RequestBody ReportNotificationRequest reportRequest) {
        reportService.createReport(reportRequest);
    }

}
