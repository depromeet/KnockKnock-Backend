package io.github.depromeet.knockknockbackend.domain.group.exception;

import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class GroupNotFoundException extends KnockException {
    public static final KnockException EXCEPTION = new GroupNotFoundException();

    private GroupNotFoundException() {
        super(ErrorCode.GROUP_NOT_FOUND);
    }
}
