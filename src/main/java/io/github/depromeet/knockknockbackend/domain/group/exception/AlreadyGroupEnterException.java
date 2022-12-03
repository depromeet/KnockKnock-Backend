package io.github.depromeet.knockknockbackend.domain.group.exception;

import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class AlreadyGroupEnterException extends KnockException {
    public static final KnockException EXCEPTION = new AlreadyGroupEnterException();

    private AlreadyGroupEnterException() {
        super(ErrorCode.ALREADY_GROUP_ENTER);
    }
}
