package io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response;

import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class AfterOauthResponse {


    @Schema(description = "어세스 토큰")
    private String accessToken;
    @Schema(description = "리프레쉬 토큰")
    private String refreshToken;
    @Schema(description = "회원가입을 했던 유저인지에 대한 여부 , oauth 요청을 통해 처음 회원가입한경우 false임")
    private Boolean isRegistered;


}
