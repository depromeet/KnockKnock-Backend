package io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.request;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class SendInstanceToMeBeforeSignUpRequest {

    @NotBlank private String token;
    @NotEmpty private String content;
}
