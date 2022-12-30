package io.github.depromeet.knockknockbackend.domain.notification.exception;


import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class ReservationAlreadyExistException extends KnockException {
    public static final KnockException EXCEPTION = new ReservationAlreadyExistException();

    private ReservationAlreadyExistException() {
        super(ErrorCode.RESERVATION_ALREADY_EXIST);
    }
}
