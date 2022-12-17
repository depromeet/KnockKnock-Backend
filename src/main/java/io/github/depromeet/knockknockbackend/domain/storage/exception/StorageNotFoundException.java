package io.github.depromeet.knockknockbackend.domain.storage.exception;


import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class StorageNotFoundException extends KnockException {
    public static final KnockException EXCEPTION = new StorageNotFoundException();

    private StorageNotFoundException() {
        super(ErrorCode.STORAGE_NOT_FOUND);
    }
}
