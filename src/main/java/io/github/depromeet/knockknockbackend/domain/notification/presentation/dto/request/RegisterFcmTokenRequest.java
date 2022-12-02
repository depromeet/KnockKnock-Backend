package io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.request;

import lombok.Getter;

@Getter
public class RegisterFcmTokenRequest {

    private String deviceId;
    private String token;

}
