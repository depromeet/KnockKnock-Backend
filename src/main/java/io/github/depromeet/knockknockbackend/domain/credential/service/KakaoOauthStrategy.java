package io.github.depromeet.knockknockbackend.domain.credential.service;


import io.github.depromeet.knockknockbackend.domain.credential.service.OauthCommonUserInfoDto.OauthCommonUserInfoDtoBuilder;
import io.github.depromeet.knockknockbackend.global.property.OauthProperties;
import io.github.depromeet.knockknockbackend.global.utils.api.client.KakaoInfoClient;
import io.github.depromeet.knockknockbackend.global.utils.api.client.KakaoOauthClient;
import io.github.depromeet.knockknockbackend.global.utils.api.dto.response.KakaoInformationResponse;
import io.github.depromeet.knockknockbackend.global.utils.api.dto.response.KakaoInformationResponse.KakaoAccount;
import io.github.depromeet.knockknockbackend.global.utils.api.dto.response.OIDCPublicKeysResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component("KAKAO")
public class KakaoOauthStrategy implements OauthStrategy {

    private static final String QUERY_STRING =
            "/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code";
    private static final String PREFIX = "Bearer ";
    private final OauthProperties oauthProperties;
    private final KakaoOauthClient kakaoOauthClient;
    private final KakaoInfoClient kakaoInfoClient;
    private final OauthOIDCProvider oauthOIDCProvider;

    private static final String ISSUER = "https://kauth.kakao.com";

    // oauthLink 발급
    public String getOauthLink() {
        return oauthProperties.getKakaoBaseUrl()
                + String.format(
                        QUERY_STRING,
                        oauthProperties.getKakaoClientId(),
                        oauthProperties.getKakaoRedirectUrl());
    }

    // 어세스토큰 발급
    public String getAccessToken(String code) {

        return kakaoOauthClient
                .kakaoAuth(
                        oauthProperties.getKakaoClientId(),
                        oauthProperties.getKakaoRedirectUrl(),
                        code,
                        oauthProperties.getKakaoClientSecret())
                .getAccessToken();
    }

    // 발급된 어세스 토큰으로 유저정보 조회
    public OauthCommonUserInfoDto getUserInfo(String oauthAccessToken) {
        KakaoInformationResponse response =
                kakaoInfoClient.kakaoUserInfo(PREFIX + oauthAccessToken);
        KakaoAccount kakaoAccount = response.getKakaoAccount();
        String oauthId = response.getId();
        OauthCommonUserInfoDtoBuilder oauthCommonUserInfoDtoBuilder =
                OauthCommonUserInfoDto.builder()
                        .oauthProvider(OauthProvider.KAKAO.getValue())
                        .oauthId(oauthId);
        // 계정정보가 널이 아니면..!
        if (kakaoAccount != null) {
            String email = kakaoAccount.getEmail();
            if (email != null) {
                oauthCommonUserInfoDtoBuilder.email(email);
            }
        }

        return oauthCommonUserInfoDtoBuilder.build();
    }

    @Override
    public OIDCDecodePayload getOIDCDecodePayload(String token) {
        OIDCPublicKeysResponse oidcPublicKeysResponse = kakaoOauthClient.getKakaoOIDCOpenKeys();
        return oauthOIDCProvider.getPayloadFromIdToken(
                token, ISSUER, oauthProperties.getKakaoClientId(), oidcPublicKeysResponse);
    }
}
