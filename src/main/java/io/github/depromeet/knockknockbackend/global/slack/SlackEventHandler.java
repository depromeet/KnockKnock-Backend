package io.github.depromeet.knockknockbackend.global.slack;


import io.github.depromeet.knockknockbackend.domain.report.domain.repository.ReportRepository;
import io.github.depromeet.knockknockbackend.domain.report.event.NewReportEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class SlackEventHandler {
    private final ReportRepository reportRepository;

    @Async
    @TransactionalEventListener(
            classes = NewReportEvent.class,
            phase = TransactionPhase.AFTER_COMMIT)
    public void handleNewReportEvent(NewReportEvent newReportEvent) {
        Long reportId = newReportEvent.getReportId();
        reportRepository.findById(reportId);
    }
}
