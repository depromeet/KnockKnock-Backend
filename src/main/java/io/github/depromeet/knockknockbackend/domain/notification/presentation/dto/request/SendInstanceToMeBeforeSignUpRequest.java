package io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.request;


import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
public class SendInstanceToMeBeforeSignUpRequest {

    @NotBlank
    private String token;
    @NotEmpty
    private String content;
}
