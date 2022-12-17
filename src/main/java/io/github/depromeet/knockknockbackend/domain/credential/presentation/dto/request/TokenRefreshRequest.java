package io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request;


import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class TokenRefreshRequest {
    @NotEmpty() private String refreshToken;
}
