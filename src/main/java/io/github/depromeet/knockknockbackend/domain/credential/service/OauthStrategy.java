package io.github.depromeet.knockknockbackend.domain.credential.service;

import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request.OauthCodeRequest;

public interface OauthStrategy {


    OauthCommonUserInfoDto getUserInfo(String oauthAccessToken);
     String getOauthLink();

    String getAccessToken(String code);
}
