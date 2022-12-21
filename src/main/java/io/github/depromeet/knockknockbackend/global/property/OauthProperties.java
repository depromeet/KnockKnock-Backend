package io.github.depromeet.knockknockbackend.global.property;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties("oauth")
public class OauthProperties {

    private OAuthSecret kakao;
    private OAuthSecret google;

    @Getter
    @Setter
    public static class OAuthSecret {
        private String baseUrl;
        private String clientId;
        private String clientSecret;
        private String redirectUrl;
        private String appId;
    }

    public String getKakaoBaseUrl() {
        return kakao.getBaseUrl();
    }

    public String getKakaoClientId() {
        return kakao.getClientId();
    }

    public String getKakaoRedirectUrl() {
        return kakao.getRedirectUrl();
    }

    public String getKakaoClientSecret() {
        return kakao.getClientSecret();
    }

    public String getKakaoAppId() {
        return kakao.getAppId();
    }

    public String getGoogleBaseUrl() {
        return google.getBaseUrl();
    }

    public String getGoogleClientId() {
        return google.getClientId();
    }

    public String getGoogleRedirectUrl() {
        return google.getRedirectUrl();
    }

    public String getGoogleClientSecret() {
        return google.getClientSecret();
    }

    public String getGoogleAppId() {
        return google.getAppId();
    }
}
