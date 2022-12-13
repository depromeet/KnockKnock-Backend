package io.github.depromeet.knockknockbackend.domain.option.exception;

import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class OptionNotFoundException extends KnockException {

    public static final KnockException EXCEPTION = new OptionNotFoundException();

    private OptionNotFoundException() {
        super(ErrorCode.OPTION_NOT_FOUND);
    }

}
