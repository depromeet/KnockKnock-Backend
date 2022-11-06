package io.github.depromeet.knockknockbackend.domain.credential.presentation;


import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request.OauthTokenRequest;
import io.github.depromeet.knockknockbackend.domain.credential.service.KakaoAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/credentials")
@RequiredArgsConstructor
public class CredentialController {


    private final KakaoAuthService kakaoAuthService;


    @PostMapping("/kakao")
    public void kakaoAccessTokenVerify(@RequestBody OauthTokenRequest oauthTokenRequest){
        System.out.println(oauthTokenRequest.getOauthAccessToken());
        System.out.println("asdfasdf");

        kakaoAuthService.checkOauthTokenValid(oauthTokenRequest);
    }
}
