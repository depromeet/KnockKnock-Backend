package io.github.depromeet.knockknockbackend.global.error;

import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //TODO : 밸리데이션 에러라던지 무언가 다른 응답이 필요한 에러항목들을... 바꿔주기!

    @ExceptionHandler(KnockException.class)
    public ResponseEntity<ErrorResponse> hanulExceptionHandler(KnockException e) {
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