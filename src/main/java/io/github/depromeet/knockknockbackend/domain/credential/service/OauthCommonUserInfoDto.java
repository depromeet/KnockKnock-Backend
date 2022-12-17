package io.github.depromeet.knockknockbackend.domain.credential.service;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OauthCommonUserInfoDto {

    // oauth인증한 사용자 고유 아이디
    private String oauthId;
    // 이메일 nullable함
    private String email;
    // oauth 제공자
    private String oauthProvider;
}
