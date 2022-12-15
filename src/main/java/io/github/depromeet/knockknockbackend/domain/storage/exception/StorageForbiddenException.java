package io.github.depromeet.knockknockbackend.domain.storage.exception;

import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class StorageForbiddenException extends KnockException {
    public static final KnockException EXCEPTION = new StorageForbiddenException();

    private StorageForbiddenException() {
        super(ErrorCode.STORAGE_FORBIDDEN);
    }
}
