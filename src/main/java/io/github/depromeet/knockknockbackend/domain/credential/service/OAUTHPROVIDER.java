package io.github.depromeet.knockknockbackend.domain.credential.service;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OAUTHPROVIDER {

    KAKAO("kakao"),
    GOOGLE("google");
    private String oauthProvider;

}
