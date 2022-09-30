package io.github.depromeet.knockknockbackend.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EXAMPLE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "EXAMPLE-404-1", "Example Not Found."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "GLOBAL-500-1", "Internal Server Error.");

    private int status;
    private String code;
    private String reason;

}
