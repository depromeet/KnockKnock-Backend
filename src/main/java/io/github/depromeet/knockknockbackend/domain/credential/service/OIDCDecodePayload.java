package io.github.depromeet.knockknockbackend.domain.credential.service;


import lombok.Getter;

@Getter
public class OIDCDecodePayload {
    /** issuer ex https://kauth.kakao.com */
    private String iss;
    /** client id */
    private String aud;
    /** oauth provider account unique id */
    private String sub;

    private String email;
}
