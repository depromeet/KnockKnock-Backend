package io.github.depromeet.knockknockbackend.domain.notification.exception;


import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class NotificationInvalidFcmToken extends KnockException {
    public static final KnockException EXCEPTION = new NotificationInvalidFcmToken();

    private NotificationInvalidFcmToken() {
        super(ErrorCode.NOTIFICATION_FCM_TOKEN_INVALID);
    }
}
