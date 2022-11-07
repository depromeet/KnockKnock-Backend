package io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    private String oauthAccessToken;
    private String oauthProvider;
}
