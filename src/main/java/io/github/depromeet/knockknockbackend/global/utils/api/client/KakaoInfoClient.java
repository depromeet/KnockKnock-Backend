package io.github.depromeet.knockknockbackend.global.utils.api.client;

import io.github.depromeet.knockknockbackend.global.utils.api.dto.response.KakaoInformationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "KakaoInfoClient", url = "https://kapi.kakao.com/v2/user/me")
public interface KakaoInfoClient {

    @GetMapping
    KakaoInformationResponse kakaoInfo(@RequestHeader("Authorization") String accessToken);

}