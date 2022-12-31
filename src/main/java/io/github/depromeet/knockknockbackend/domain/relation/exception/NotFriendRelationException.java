package io.github.depromeet.knockknockbackend.domain.relation.exception;


import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class NotFriendRelationException extends KnockException {

    public static final KnockException EXCEPTION = new NotFriendRelationException();

    private NotFriendRelationException() {
        super(ErrorCode.NOT_FRIEND_RELATION);
    }
}
