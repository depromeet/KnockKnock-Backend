package io.github.depromeet.knockknockbackend.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EXAMPLE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "EXAMPLE-404-1", "Example Not Found."),

    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "GLOBAL-401-1", "Expired Jwt Token."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), "GLOBAL-401-1", "Invalid Jwt Token."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "GLOBAL-404-1", "User Not Found."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "GLOBAL-500-1", "Internal Server Error."),

    OAUTH_TOKEN_INVALID(401, "CREDENTIAL-401-1", "Oauth Token Invalid"),
    ALREADY_SIGNUP_USER(400, "CREDENTIAL-400-1", "user is already signup"),

    ALREADY_SEND_REQUEST(400, "RELATION-400-1", "Already Send Request."),

    OPTION_NOT_FOUND(404, "OPTION-404-1", "Option Not Found."),

    OTHER_SERVER_BAD_REQUEST(400, "FEIGN-400-1", "Other server bad request"),
    OTHER_SERVER_UNAUTHORIZED(401, "FEIGN-401-1", "Other server unauthorized"),
    OTHER_SERVER_FORBIDDEN(403, "FEIGN-403-1", "Other server forbidden"),
    OTHER_SERVER_EXPIRED_TOKEN(419, "FEIGN-419-1", "Other server expired token"),
    CATEGORY_NOT_FOUND(404,"GROUP-404-1", "Category Not Found" ),
    BACKGROUND_NOT_FOUND(404,"GROUP-404-2", "BACKGROUND Not Found"),

    THUMBNAIL_NOT_FOUND(404,"GROUP-404-3", "THUMBNAIL Not Found");
    private int status;
    private String code;
    private String reason;

}
