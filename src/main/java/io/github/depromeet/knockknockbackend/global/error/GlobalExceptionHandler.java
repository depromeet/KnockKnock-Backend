package io.github.depromeet.knockknockbackend.global.error;

import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ErrorCode validationError = ErrorCode.VALIDATION_ERROR;
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(validationError.getStatus(),
                validationError.getCode(),
                e.getMessage())
            );
    }
    @ExceptionHandler(KnockException.class)
    public ResponseEntity<ErrorResponse> KnockExceptionHandler(KnockException e) {
        ErrorCode code = e.getErrorCode();
        return new ResponseEntity<>(new ErrorResponse(code.getStatus(), code.getCode(), code.getReason()),
            HttpStatus.valueOf(code.getStatus()));
    }


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("INTERNAL_SERVER_ERROR", e);
        ErrorCode internalServerError = ErrorCode.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(new ErrorResponse(internalServerError.getStatus(),
            internalServerError.getCode(),
            internalServerError.getReason()),
            HttpStatus.valueOf(internalServerError.getStatus()));
    }
}