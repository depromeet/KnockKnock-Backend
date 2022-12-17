package io.github.depromeet.knockknockbackend.domain.asset.exception;

import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class AppVersionNotFoundException extends KnockException {

    public static final KnockException EXCEPTION = new AppVersionNotFoundException();

    private AppVersionNotFoundException() {
        super(ErrorCode.APP_VERSION_NOT_FOUND);
    }

}
