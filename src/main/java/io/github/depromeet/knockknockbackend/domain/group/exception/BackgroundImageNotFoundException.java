package io.github.depromeet.knockknockbackend.domain.group.exception;

import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class BackgroundImageNotFoundException extends KnockException {

    public static final KnockException EXCEPTION = new BackgroundImageNotFoundException();

    private BackgroundImageNotFoundException() {
        super(ErrorCode.BACKGROUND_NOT_FOUND);
    }

}
