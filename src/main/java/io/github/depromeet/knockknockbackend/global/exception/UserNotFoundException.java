package io.github.depromeet.knockknockbackend.global.exception;

import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class UserNotFoundException extends KnockException {

    public static final KnockException EXCEPTION = new UserNotFoundException();

    private UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }

}
