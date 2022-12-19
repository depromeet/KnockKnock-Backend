package io.github.depromeet.knockknockbackend.domain.notification.presentation.dto.request;


import lombok.Getter;

@Getter
public class SendInstanceToMeBeforeSignUpRequest {

    private String token;
    private String title;
    private String content;
    private String imageUrl;
}
