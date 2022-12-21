package io.github.depromeet.knockknockbackend.domain.relation.exception;

import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class FriendRequestNotFoundException extends KnockException {

    public static final KnockException EXCEPTION = new FriendRequestNotFoundException();

    private FriendRequestNotFoundException() {
        super(ErrorCode.FRIEND_REQUEST_NOT_FOUND);
    }

}
