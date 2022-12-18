package io.github.depromeet.knockknockbackend.domain.group.exception;


import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class NotMemberException extends KnockException {
    public static final KnockException EXCEPTION = new NotMemberException();

    private NotMemberException() {
        super(ErrorCode.NOT_GROUP_MEMBER);
    }
}
