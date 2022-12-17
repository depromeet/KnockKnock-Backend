package io.github.depromeet.knockknockbackend.domain.asset.exception;


import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class ProfileImageNotFoundException extends KnockException {

    public static final KnockException EXCEPTION = new ProfileImageNotFoundException();

    private ProfileImageNotFoundException() {
        super(ErrorCode.PROFILE_IMAGE_NOT_FOUND);
    }
}
