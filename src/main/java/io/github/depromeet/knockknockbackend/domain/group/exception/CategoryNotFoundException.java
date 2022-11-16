package io.github.depromeet.knockknockbackend.domain.group.exception;

import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class CategoryNotFoundException extends KnockException {

    public static final KnockException EXCEPTION = new CategoryNotFoundException();

    private CategoryNotFoundException() {
        super(ErrorCode.CATEGORY_NOT_FOUND);
    }

}
