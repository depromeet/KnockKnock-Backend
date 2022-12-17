package io.github.depromeet.knockknockbackend.domain.example.exception;


import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

public class ExampleNotFoundException extends KnockException {

    public static final KnockException EXCEPTION = new ExampleNotFoundException();

    private ExampleNotFoundException() {
        super(ErrorCode.EXAMPLE_NOT_FOUND);
    }
}
