package io.github.depromeet.knockknockbackend.global.utils.api.client;


import io.github.depromeet.knockknockbackend.global.utils.api.dto.response.OauthAccessTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "GoogleAuthClient", url = "https://www.googleapis.com/oauth2/v4")
public interface GoogleAuthClient {

    // EmptyDto 411 구글에서 content length 없다고함
    // https://github.com/OpenFeign/feign/issues/1251
    @PostMapping(
            "/token?grant_type=authorization_code&client_id={CLIENT_ID}&redirect_uri={REDIRECT_URI}&code={CODE}&client_secret={CLIENT_SECRET}")
    OauthAccessTokenResponse googleAuth(
            @PathVariable("CLIENT_ID") String clientId,
            @PathVariable("REDIRECT_URI") String redirectUri,
            @PathVariable("CODE") String code,
            @PathVariable("CLIENT_SECRET") String client_secret);
}
