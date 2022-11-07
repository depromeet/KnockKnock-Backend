package io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterResponse {

    private String accessToken;
    private String refreshToken;
    private UserProfileDto userProfileDto;

}
