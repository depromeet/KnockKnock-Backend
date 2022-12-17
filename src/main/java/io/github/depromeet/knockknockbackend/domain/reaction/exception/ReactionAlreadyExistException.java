package io.github.depromeet.knockknockbackend.domain.reaction.exception;


import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class ReactionAlreadyExistException extends KnockException {
    public static final KnockException EXCEPTION = new ReactionAlreadyExistException();

    private ReactionAlreadyExistException() {
        super(ErrorCode.REACTION_ALREADY_EXIST);
    }
}
