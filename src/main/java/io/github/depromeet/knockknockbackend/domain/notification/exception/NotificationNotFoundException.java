package io.github.depromeet.knockknockbackend.domain.notification.exception;

import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class NotificationNotFoundException extends KnockException {
    public static final KnockException EXCEPTION = new NotificationNotFoundException();

    private NotificationNotFoundException() {
        super(ErrorCode.NOTIFICATION_NOT_FOUND);
    }
}
