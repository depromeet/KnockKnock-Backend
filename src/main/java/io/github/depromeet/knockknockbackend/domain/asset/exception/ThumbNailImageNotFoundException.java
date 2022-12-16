package io.github.depromeet.knockknockbackend.domain.asset.exception;

import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class ThumbNailImageNotFoundException extends KnockException {

    public static final KnockException EXCEPTION = new ThumbNailImageNotFoundException();

    private ThumbNailImageNotFoundException() {
        super(ErrorCode.THUMBNAIL_NOT_FOUND);
    }

}
