package io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request;


import io.github.depromeet.knockknockbackend.domain.credential.service.OauthProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterRequest {

    private String oauthAccessToken;

    private OauthProvider oauthProvider;

    private String nickName;

}
