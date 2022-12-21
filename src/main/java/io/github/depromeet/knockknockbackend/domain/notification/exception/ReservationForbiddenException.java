package io.github.depromeet.knockknockbackend.domain.notification.exception;


import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class ReservationForbiddenException extends KnockException {
    public static final KnockException EXCEPTION = new ReservationForbiddenException();

    private ReservationForbiddenException() {
        super(ErrorCode.RESERVATION_FORBIDDEN);
    }
}
