package io.github.depromeet.knockknockbackend.domain.reaction.exception;

import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class ReactionForbiddenException extends KnockException {
    public static final KnockException EXCEPTION = new ReactionForbiddenException();

    private ReactionForbiddenException() {
        super(ErrorCode.REACTION_FORBIDDEN);
    }
}
