package io.github.depromeet.knockknockbackend.global.error.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    EXAMPLE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "EXAMPLE-404-1", "Example Not Found."),

    ARGUMENT_NOT_VALID_ERROR(HttpStatus.BAD_REQUEST.value(), "GLOBAL-400-1", "validation error"),

    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "GLOBAL-401-1", "Expired Jwt Token."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), "GLOBAL-401-1", "Invalid Jwt Token."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "GLOBAL-404-1", "User Not Found."),

    INTERNAL_SERVER_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR.value(), "GLOBAL-500-1", "Internal Server Error."),

    OAUTH_TOKEN_INVALID(401, "CREDENTIAL-401-1", "Oauth Token Invalid"),
    ALREADY_SIGNUP_USER(400, "CREDENTIAL-400-1", "user is already signup"),

    ALREADY_SEND_REQUEST(400, "RELATION-400-1", "Already Send Request."),
    ALREADY_FRIEND_REQUEST(400, "RELATION-400-1", "Already Friend Request."),
    FRIEND_REQUEST_NOT_FOUND(404, "RELATION-404-1", "Friend Request Not Found."),

    OPTION_NOT_FOUND(404, "OPTION-404-1", "Option Not Found."),

    OTHER_SERVER_BAD_REQUEST(400, "FEIGN-400-1", "Other server bad request"),
    OTHER_SERVER_UNAUTHORIZED(401, "FEIGN-401-1", "Other server unauthorized"),
    OTHER_SERVER_FORBIDDEN(403, "FEIGN-403-1", "Other server forbidden"),
    OTHER_SERVER_EXPIRED_TOKEN(419, "FEIGN-419-1", "Other server expired token"),
    CATEGORY_NOT_FOUND(404, "GROUP-404-1", "Category Not Found"),

    GROUP_NOT_FOUND(404, "GROUP-404-1", "Group Not Found"),

    GROUP_NOT_HOST(400, "GROUP-400-1", "User Not Host"),

    ALREADY_GROUP_ENTER(400, "GROUP-400-3", "Already enter group"),
    ADMISSION_NOT_FOUND(404, "GROUP-404-4", "ADMISSION Not Found"),

    HOST_CAN_NOT_LEAVE(400, "GROUP-400-2", "Host can not leave from own group"),
    NOT_GROUP_MEMBER(400, "GROUP-400-3", "Not member of group"),
    INVALID_INVITE_TOKEN(400, "GROUP-400-4", "invalid invite token"),

    REACTION_ALREADY_EXIST(
            HttpStatus.BAD_REQUEST.value(),
            "REACTION-400-1",
            "reaction of the notification is already exist"),
    REACTION_FORBIDDEN(
            HttpStatus.FORBIDDEN.value(), "REACTION-403-1", "the user cannot change the reaction"),

    NOTIFICATION_FORBIDDEN(
            HttpStatus.FORBIDDEN.value(),
            "NOTIFICATION-403-1",
            "The user has no access to the notification"),
    NOTIFICATION_NOT_FOUND(
            HttpStatus.NOT_FOUND.value(), "NOTIFICATION-404-1", "Notification Not Found"),
    NOTIFICATION_FCM_FAIL_SEND(
            HttpStatus.INTERNAL_SERVER_ERROR.value(), "NOTIFICATION-500-1", "FCM ERROR"),

    RESERVATION_NOT_FOUND(
            HttpStatus.NOT_FOUND.value(), "RESERVATION-404-1", "Reservation Not Found"),
    RESERVATION_FORBIDDEN(
            HttpStatus.FORBIDDEN.value(),
            "RESERVATION-403-1",
            "The user has no access to the reservation"),

    STORAGE_FORBIDDEN(
            HttpStatus.FORBIDDEN.value(), "STORAGE-403-1", "The user has no access to the storage"),
    STORAGE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "STORAGE-404-1", "Storage Not Found"),

    FILE_EMPTY(HttpStatus.BAD_REQUEST.value(), "IMAGE-400-1", "File is Empty."),
    BAD_FILE_EXTENSION(HttpStatus.BAD_REQUEST.value(), "IMAGE-400-1", "Bad File Extension."),
    FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR.value(), "IMAGE-500-1", "File upload fail"),

    PROFILE_IMAGE_NOT_FOUND(404, "ASSET-404-1", "PROFILE Not Found"),
    BACKGROUND_NOT_FOUND(404, "ASSET-404-2", "BACKGROUND Not Found"),
    THUMBNAIL_NOT_FOUND(404, "ASSET-404-3", "THUMBNAIL Not Found"),
    APP_VERSION_NOT_FOUND(404, "ASSET-404-4", "APP_VERSION Not Found"),

    USER_CREDENTIAL_FORBIDDEN(403, "CREDENTIAL-403-1", "user status is not normal"),
    CANNOT_REPORT_ME(400, "REPORT-400-1", "cannot report me"),
    REPORT_NOT_FOUND(404, "REPORT-404-1", "REPORT_NOT_FOUND");

    private int status;
    private String code;
    private String reason;
}
