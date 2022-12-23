package io.github.depromeet.knockknockbackend.domain.report.domain.repository;


import io.github.depromeet.knockknockbackend.domain.report.domain.Report;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ReportRepository extends CrudRepository<Report, Long> {
    Optional<Report> findByReporterIdAndNotificationId(Long reporterId, Long notificationId);
}
