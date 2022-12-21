package io.github.depromeet.knockknockbackend.domain.relation.exception;

import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class AlreadyFriendException extends KnockException {

    public static final KnockException EXCEPTION = new AlreadyFriendException();

    private AlreadyFriendException() {
        super(ErrorCode.ALREADY_FRIEND_REQUEST);
    }

}
