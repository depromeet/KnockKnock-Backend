package io.github.depromeet.knockknockbackend.domain.report.service;


import io.github.depromeet.knockknockbackend.domain.report.domain.repository.ReportRepository;
import io.github.depromeet.knockknockbackend.domain.report.presentation.dto.request.ReportNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ReportService {
    private final ReportRepository reportRepository;


    public void createReport(ReportNotificationRequest reportRequest) {
    }
}
