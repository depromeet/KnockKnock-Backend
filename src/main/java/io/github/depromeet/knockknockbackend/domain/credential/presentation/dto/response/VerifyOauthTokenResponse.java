package io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VerifyOauthTokenResponse {
    private Boolean isRegistered;
    private String oauthProvider;
    private String oauthId;

}
