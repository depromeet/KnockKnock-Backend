package io.github.depromeet.knockknockbackend.domain.notification.service;


import io.github.depromeet.knockknockbackend.domain.notification.domain.DeviceToken;
import io.github.depromeet.knockknockbackend.domain.notification.domain.Reservation;
import io.github.depromeet.knockknockbackend.domain.notification.domain.repository.ReservationRepository;
import io.github.depromeet.knockknockbackend.infrastructor.fcm.FcmService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationService {

    private final FcmService fcmService;
    private final NotificationService notificationService;

    private final ReservationRepository reservationRepository;

    @Transactional
    public void processScheduledReservation() {
        log.info("**Reservation Scheduled process time:" + LocalDateTime.now());

        List<Reservation> reservations = retrieveReservation();
        if (reservations.isEmpty()) {
            log.info("**Reservation Scheduled data is nothing");
            return;
        }
        deleteReservation(
                reservations.stream().map(Reservation::getId).collect(Collectors.toList()));

        reservations.forEach(
                reservation -> {
                    List<DeviceToken> deviceTokens =
                            notificationService.getDeviceTokens(
                                    reservation.getGroup().getId(),
                                    reservation.getSendUser().getId());
                    List<String> tokens = notificationService.getFcmTokens(deviceTokens);

                    fcmService.sendGroupMessage(
                            tokens,
                            reservation.getTitle(),
                            reservation.getContent(),
                            reservation.getImageUrl());

                    notificationService.recordNotification(
                            deviceTokens,
                            reservation.getTitle(),
                            reservation.getContent(),
                            reservation.getImageUrl(),
                            reservation.getGroup(),
                            reservation.getSendUser(),
                            reservation.getCreatedDate());
                });
    }

    private List<Reservation> retrieveReservation() {
        return reservationRepository.findBySendAtLessThan(LocalDateTime.now());
    }

    private void deleteReservation(List<Long> reservationIds) {
        reservationRepository.deleteByIdIn(reservationIds);
    }
}
