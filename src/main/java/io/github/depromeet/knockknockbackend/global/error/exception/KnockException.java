package io.github.depromeet.knockknockbackend.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KnockException extends RuntimeException {

    private ErrorCode errorCode;

}
