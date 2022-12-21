package io.github.depromeet.knockknockbackend.global.utils.api.client;


import feign.Headers;
import io.github.depromeet.knockknockbackend.global.utils.api.dto.response.GoogleInformationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "GoogleUnlinkClient", url = "https://oauth2.googleapis.com/revoke")
public interface GoogleUnlinkClient {

    @PostMapping
    @Headers("Content-type:application/x-www-form-urlencoded")
    GoogleInformationResponse unlink(@RequestParam("token") String oauthAccessToken);
}
