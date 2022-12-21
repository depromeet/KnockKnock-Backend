package io.github.depromeet.knockknockbackend.domain.credential.exception;


import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class ForbiddenUserException extends KnockException {
    public static final KnockException EXCEPTION = new ForbiddenUserException();

    private ForbiddenUserException() {
        super(ErrorCode.USER_CREDENTIAL_FORBIDDEN);
    }
}
