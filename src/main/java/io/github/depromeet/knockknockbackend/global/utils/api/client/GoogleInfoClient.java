package io.github.depromeet.knockknockbackend.global.utils.api.client;


import io.github.depromeet.knockknockbackend.global.utils.api.dto.response.GoogleInformationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "GoogleInfoClient", url = "https://www.googleapis.com/oauth2/v1/userinfo")
public interface GoogleInfoClient {

    @GetMapping("?alt=json")
    GoogleInformationResponse googleInfo(@RequestHeader("Authorization") String token);
}
