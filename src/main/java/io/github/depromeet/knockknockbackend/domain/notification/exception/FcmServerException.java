package io.github.depromeet.knockknockbackend.domain.notification.exception;


import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class FcmServerException extends KnockException {
    public static final KnockException EXCEPTION = new FcmServerException();

    private FcmServerException() {
        super(ErrorCode.NOTIFICATION_FCM_SERVER_ERROR);
    }
}
