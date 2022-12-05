package io.github.depromeet.knockknockbackend.domain.group.exception;

import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class InviteNotFoundException extends KnockException {
    public static final KnockException EXCEPTION = new InviteNotFoundException();

    private InviteNotFoundException() {
        super(ErrorCode.INVITE_NOT_FOUND);
    }
}
