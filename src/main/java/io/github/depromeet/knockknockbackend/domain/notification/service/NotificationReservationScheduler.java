package io.github.depromeet.knockknockbackend.domain.notification.service;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationReservationScheduler {

    private final ReservationService reservationService;

    @Scheduled(cron = "0 0/1 * * * *")
    public void reservationNotification() {
        reservationService.processScheduledReservation();
    }
}
