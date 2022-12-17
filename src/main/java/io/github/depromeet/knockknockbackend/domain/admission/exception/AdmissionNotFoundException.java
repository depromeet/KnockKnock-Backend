package io.github.depromeet.knockknockbackend.domain.admission.exception;


import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class AdmissionNotFoundException extends KnockException {
    public static final KnockException EXCEPTION = new AdmissionNotFoundException();

    private AdmissionNotFoundException() {
        super(ErrorCode.ADMISSION_NOT_FOUND);
    }
}
