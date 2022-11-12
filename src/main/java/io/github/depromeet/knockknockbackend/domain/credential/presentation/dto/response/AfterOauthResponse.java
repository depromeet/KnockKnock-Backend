package io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response;

import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class AfterOauthResponse {

    private String accessToken;
    private String refreshToken;
    private Boolean isRegistered;


}
