package io.github.depromeet.knockknockbackend.domain.report.exception;


import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class CannotReportMeException extends KnockException {
    public static final KnockException EXCEPTION = new CannotReportMeException();

    private CannotReportMeException() {
        super(ErrorCode.CANNOT_REPORT_ME);
    }
}
