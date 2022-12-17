package io.github.depromeet.knockknockbackend.global.error;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException e, HttpServletRequest request)
            throws JsonProcessingException {
        ErrorCode validationError = ErrorCode.ARGUMENT_NOT_VALID_ERROR;
        List<FieldError> errors = e.getBindingResult().getFieldErrors();

        Map<String, Object> fieldAndErrorMessages =
                errors.stream()
                        .collect(
                                Collectors.toMap(
                                        FieldError::getField, FieldError::getDefaultMessage));

        String errorsToJsonString = new ObjectMapper().writeValueAsString(fieldAndErrorMessages);
        ErrorResponse errorResponse =
                new ErrorResponse(
                        validationError.getStatus(),
                        validationError.getCode(),
                        errorsToJsonString,
                        request.getRequestURL().toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(KnockException.class)
    public ResponseEntity<ErrorResponse> KnockExceptionHandler(
            KnockException e, HttpServletRequest request) {
        ErrorCode code = e.getErrorCode();
        ErrorResponse errorResponse =
                new ErrorResponse(
                        code.getStatus(),
                        code.getCode(),
                        code.getReason(),
                        request.getRequestURL().toString());
        return ResponseEntity.status(HttpStatus.valueOf(code.getStatus())).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(
            Exception e, HttpServletRequest request) {
        log.error("INTERNAL_SERVER_ERROR", e);
        ErrorCode internalServerError = ErrorCode.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse =
                new ErrorResponse(
                        internalServerError.getStatus(),
                        internalServerError.getCode(),
                        internalServerError.getReason(),
                        request.getRequestURL().toString());
        return ResponseEntity.status(HttpStatus.valueOf(internalServerError.getStatus()))
                .body(errorResponse);
    }
}
