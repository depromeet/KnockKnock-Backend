package io.github.depromeet.knockknockbackend.domain.group.exception;

import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class NotHostException extends KnockException {
    public static final KnockException EXCEPTION = new NotHostException();

    private NotHostException() {
        super(ErrorCode.GROUP_NOT_HOST);
    }
}
