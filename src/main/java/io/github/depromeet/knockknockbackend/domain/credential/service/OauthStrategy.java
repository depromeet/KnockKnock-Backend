package io.github.depromeet.knockknockbackend.domain.credential.service;

public interface OauthStrategy {


    OauthCommonUserInfoDto getUserInfo(String oauthAccessToken);
    void checkOauthTokenValid(String oauthAccessToken);
}
