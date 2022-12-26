package io.github.depromeet.knockknockbackend.domain.report.exception;


import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class ReportNotFoundException extends KnockException {
    public static final KnockException EXCEPTION = new ReportNotFoundException();

    private ReportNotFoundException() {
        super(ErrorCode.REPORT_NOT_FOUND);
    }
}
