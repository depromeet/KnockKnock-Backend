package io.github.depromeet.knockknockbackend.domain.credential.service;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OauthProvider {

    KAKAO("kakao"),
    GOOGLE("google");
    private String oauthProviderName;


    @JsonCreator
    public static OauthProvider from(String value) {
        for (OauthProvider provider : OauthProvider.values()) {
            if (provider.getValue().equals(value)) {
                return provider;
            }
        }
        return null;
    }

    @JsonValue
    public String getValue() {
        return oauthProviderName;
    }
}
