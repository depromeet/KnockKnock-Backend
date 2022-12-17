package io.github.depromeet.knockknockbackend.global.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e)
        throws JsonProcessingException {
        ErrorCode validationError = ErrorCode.VALIDATION_ERROR;
        List<FieldError> errors = e.getBindingResult().getFieldErrors();

        Map<String, Object> fieldAndErrorMessages = errors.stream()
            .collect(Collectors.toMap(
                FieldError::getField, FieldError::getDefaultMessage)
            );

        String errorsToJsonString = new ObjectMapper().writeValueAsString(fieldAndErrorMessages);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(validationError.getStatus(),
                validationError.getCode(),
                errorsToJsonString)
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