package io.github.depromeet.knockknockbackend.domain.group.exception;


import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class HostCanNotLeaveGroupException extends KnockException {
    public static final KnockException EXCEPTION = new HostCanNotLeaveGroupException();

    private HostCanNotLeaveGroupException() {
        super(ErrorCode.HOST_CAN_NOT_LEAVE);
    }
}
