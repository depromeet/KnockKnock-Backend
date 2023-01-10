package io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.request;


import lombok.Getter;
import javax.validation.constraints.NotBlank;

@Getter
public class RegisterFcmTokenRequest {
    @NotBlank
    private String deviceId;
    @NotBlank
    private String token;
}
