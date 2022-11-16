package io.github.depromeet.knockknockbackend.domain.relation.exception;

import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class AlreadySendRequestException extends KnockException {

    public static final KnockException EXCEPTION = new AlreadySendRequestException();

    private AlreadySendRequestException() {
        super(ErrorCode.ALREADY_SEND_REQUEST);
    }

}
