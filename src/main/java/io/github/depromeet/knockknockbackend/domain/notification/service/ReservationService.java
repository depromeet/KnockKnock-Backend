package io.github.depromeet.knockknockbackend.domain.notification.service;


import io.github.depromeet.knockknockbackend.domain.group.domain.Group;
import io.github.depromeet.knockknockbackend.domain.notification.domain.DeviceToken;
import io.github.depromeet.knockknockbackend.domain.notification.domain.Reservation;
import io.github.depromeet.knockknockbackend.domain.notification.domain.repository.ReservationRepository;
import io.github.depromeet.knockknockbackend.domain.notification.exception.ReservationForbiddenException;
import io.github.depromeet.knockknockbackend.domain.notification.exception.ReservationNotFoundException;
import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.request.ChangeSendAtReservationRequest;
import io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.request.SendReservationRequest;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.global.utils.security.SecurityUtils;
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

    public void sendReservation(SendReservationRequest request) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        reservationRepository.save(
                Reservation.of(
                        request.getSendAt(),
                        request.getTitle(),
                        request.getContent(),
                        request.getImageUrl(),
                        Group.of(request.getGroupId()),
                        User.of(currentUserId)));
    }

    public void changeSendAtReservation(ChangeSendAtReservationRequest request) {
        Reservation reservation = queryReservationById(request.getReservationId());
        reservation.changeSendAt(request.getSendAt());
        reservationRepository.save(reservation);
    }

    @Transactional
    public void deleteReservation(Long reservationId) {
        Reservation reservation = queryReservationById(reservationId);
        validateDeletePermissionReservation(reservation);
        reservationRepository.delete(reservation);
    }

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

    private Reservation queryReservationById(Long reservationId) {
        return reservationRepository
                .findById(reservationId)
                .orElseThrow(() -> ReservationNotFoundException.EXCEPTION);
    }

    private void validateDeletePermissionReservation(Reservation reservation) {
        if (!SecurityUtils.getCurrentUserId().equals(reservation.getSendUser().getId())) {
            throw ReservationForbiddenException.EXCEPTION;
        }
    }
}