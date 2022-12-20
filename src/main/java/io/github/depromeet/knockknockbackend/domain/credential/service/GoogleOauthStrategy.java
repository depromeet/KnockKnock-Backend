package io.github.depromeet.knockknockbackend.domain.credential.service;


import io.github.depromeet.knockknockbackend.domain.credential.service.OauthCommonUserInfoDto.OauthCommonUserInfoDtoBuilder;
import io.github.depromeet.knockknockbackend.global.property.OauthProperties;
import io.github.depromeet.knockknockbackend.global.utils.api.client.GoogleAuthClient;
import io.github.depromeet.knockknockbackend.global.utils.api.client.GoogleInfoClient;
import io.github.depromeet.knockknockbackend.global.utils.api.dto.response.GoogleInformationResponse;
import io.github.depromeet.knockknockbackend.global.utils.api.dto.response.OIDCPublicKeysResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component("GOOGLE")
public class GoogleOauthStrategy implements OauthStrategy {

    private static final String QUERY_STRING =
            "/o/oauth2/v2/auth?client_id=%s&redirect_uri=%s&response_type=code&scope=%s";
    private static final String PREFIX = "Bearer ";
    private static final String scope = "https://www.googleapis.com/auth/userinfo.email";
    private final OauthProperties oauthProperties;
    private final GoogleAuthClient googleAuthClient;
    private final GoogleInfoClient googleInfoClient;

    private final OauthOIDCProvider oauthOIDCProvider;

    private static final String ISSUER = "https://accounts.google.com";

    // oauthLink 발급
    public String getOauthLink() {

        return oauthProperties.getGoogleBaseUrl()
                + String.format(
                        QUERY_STRING,
                        oauthProperties.getGoogleClientId(),
                        oauthProperties.getGoogleRedirectUrl(),
                        scope);
    }

    // 어세스토큰 발급
    public String getAccessToken(String code) {
        String decodedCode = URLDecoder.decode(code, StandardCharsets.UTF_8);

        return googleAuthClient
                .googleAuth(
                        oauthProperties.getGoogleClientId(),
                        oauthProperties.getGoogleRedirectUrl(),
                        decodedCode,
                        oauthProperties.getGoogleClientSecret())
                .getAccessToken();
    }

    @Override
    public OIDCDecodePayload getOIDCDecodePayload(String token) {
        OIDCPublicKeysResponse oidcPublicKeysResponse = googleAuthClient.getGoogleOIDCOpenKeys();
        return oauthOIDCProvider.getPayloadFromIdToken(
                token, ISSUER, oauthProperties.getGoogleClientId(), oidcPublicKeysResponse);
    }
    // 발급된 어세스 토큰으로 유저정보 조회
    public OauthCommonUserInfoDto getUserInfo(String oauthAccessToken) {

        GoogleInformationResponse response = googleInfoClient.googleInfo(PREFIX + oauthAccessToken);
        String oauthId = response.getId();
        OauthCommonUserInfoDtoBuilder oauthCommonUserInfoDtoBuilder =
                OauthCommonUserInfoDto.builder()
                        .oauthProvider(OauthProvider.GOOGLE.getValue())
                        .oauthId(oauthId);

        String email = response.getEmail();

        // 이메일 정보가 널이 아니면
        if (email != null) {
            oauthCommonUserInfoDtoBuilder.email(email);
        }

        return oauthCommonUserInfoDtoBuilder.build();
    }
}
