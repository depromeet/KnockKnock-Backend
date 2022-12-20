package io.github.depromeet.knockknockbackend.domain.credential.service;


import io.github.depromeet.knockknockbackend.global.security.JwtOIDCProvider;
import io.github.depromeet.knockknockbackend.global.utils.api.dto.response.OIDCPublicKeyDto;
import io.github.depromeet.knockknockbackend.global.utils.api.dto.response.OIDCPublicKeysResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OauthOIDCProvider {

    private final JwtOIDCProvider jwtOIDCProvider;

    private String getKidFromUnsignedIdToken(String token, String iss, String aud) {
        return jwtOIDCProvider.getKidFromUnsignedTokenHeader(token, iss, aud);
    }

    public OIDCDecodePayload getPayloadFromIdToken(
            String token, String iss, String aud, OIDCPublicKeysResponse oidcPublicKeysResponse) {
        String kid = getKidFromUnsignedIdToken(token, iss, aud);

        OIDCPublicKeyDto oidcPublicKeyDto =
                oidcPublicKeysResponse.getKeys().stream()
                        .filter(o -> o.getKid().equals(kid))
                        .findFirst()
                        .orElseThrow();

        return (OIDCDecodePayload)
                jwtOIDCProvider.getOIDCTokenBody(
                        token, oidcPublicKeyDto.getN(), oidcPublicKeyDto.getE());
    }
}
