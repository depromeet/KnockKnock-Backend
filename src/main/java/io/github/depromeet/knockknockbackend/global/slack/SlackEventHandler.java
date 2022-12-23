package io.github.depromeet.knockknockbackend.global.slack;


import io.github.depromeet.knockknockbackend.domain.report.event.NewReportEvent;
import io.github.depromeet.knockknockbackend.domain.report.service.ReportUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class SlackEventHandler {
    private final ReportUtils reportUtils;

    @Async
    @TransactionalEventListener(
            classes = NewReportEvent.class,
            phase = TransactionPhase.AFTER_COMMIT)
    public void handleNewReportEvent(NewReportEvent newReportEvent) {
        Long reportId = newReportEvent.getReportId();
        reportUtils.queryReport(reportId);
    }
}
