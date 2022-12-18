package io.github.depromeet.knockknockbackend.domain.notification.exception;


import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class NotificationForbiddenException extends KnockException {
    public static final KnockException EXCEPTION = new NotificationForbiddenException();

    private NotificationForbiddenException() {
        super(ErrorCode.NOTIFICATION_FORBIDDEN);
    }
}
