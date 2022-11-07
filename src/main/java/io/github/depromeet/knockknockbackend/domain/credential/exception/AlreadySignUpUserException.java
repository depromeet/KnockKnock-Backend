package io.github.depromeet.knockknockbackend.domain.credential.exception;

import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class AlreadySignUpUserException extends KnockException {

    public static final KnockException EXCEPTION = new AlreadySignUpUserException();

    private AlreadySignUpUserException() {
        super(ErrorCode.ALREADY_SIGNUP_USER);

    }
}
