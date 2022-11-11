package io.github.depromeet.knockknockbackend.global.error;

import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(KnockException.class)
    public ResponseEntity<ErrorResponse> hanulExceptionHandler(KnockException e) {
        ErrorCode code = e.getErrorCode();
        return new ResponseEntity<>(new ErrorResponse(code.getStatus(), code.getCode(), code.getReason()),
            HttpStatus.valueOf(code.getStatus()));
    }

}