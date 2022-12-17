package io.github.depromeet.knockknockbackend.domain.group.exception;


import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class InvalidInviteTokenException extends KnockException {
    public static final KnockException EXCEPTION = new InvalidInviteTokenException();

    private InvalidInviteTokenException() {
        super(ErrorCode.INVALID_INVITE_TOKEN);
    }
}
