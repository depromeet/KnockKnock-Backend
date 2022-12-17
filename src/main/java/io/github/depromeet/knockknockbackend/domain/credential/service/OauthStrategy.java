package io.github.depromeet.knockknockbackend.domain.credential.service;

public interface OauthStrategy {

    OauthCommonUserInfoDto getUserInfo(String oauthAccessToken);

    String getOauthLink();

    String getAccessToken(String code);
}
