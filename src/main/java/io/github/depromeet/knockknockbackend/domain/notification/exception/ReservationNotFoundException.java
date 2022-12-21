package io.github.depromeet.knockknockbackend.domain.notification.exception;


import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class ReservationNotFoundException extends KnockException {
    public static final KnockException EXCEPTION = new ReservationNotFoundException();

    private ReservationNotFoundException() {
        super(ErrorCode.RESERVATION_NOT_FOUND);
    }
}
