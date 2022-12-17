package io.github.depromeet.knockknockbackend.domain.image.exception;

import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class FileUploadFailException extends KnockException {

    public static final KnockException EXCEPTION = new FileUploadFailException();

    private FileUploadFailException() {
        super(ErrorCode.FILE_UPLOAD_FAIL);
    }

}
