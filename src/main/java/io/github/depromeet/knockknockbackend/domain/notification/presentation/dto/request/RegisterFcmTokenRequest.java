package io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.request;


import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RegisterFcmTokenRequest {
    @NotBlank private String deviceId;
    @NotBlank private String token;
}
