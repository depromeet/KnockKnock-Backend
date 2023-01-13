package io.github.depromeet.knockknockbackend.domain.reaction.exception;


import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class ReactionNotExistException extends KnockException {
    public static final KnockException EXCEPTION = new ReactionNotExistException();

    private ReactionNotExistException() {
        super(ErrorCode.REACTION_NOT_EXIST);
    }
}
