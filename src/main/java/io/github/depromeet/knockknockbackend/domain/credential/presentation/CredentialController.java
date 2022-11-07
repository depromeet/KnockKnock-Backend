package io.github.depromeet.knockknockbackend.domain.credential.presentation;


import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request.LoginRequest;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request.OauthTokenRequest;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request.RegisterRequest;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.RegisterResponse;
import io.github.depromeet.knockknockbackend.domain.credential.service.KakaoAuthService;
import io.github.depromeet.knockknockbackend.domain.credential.service.OauthCommonUserInfoDto;
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


    @PostMapping("/verify/kakao")
    public OauthCommonUserInfoDto kakaoAccessTokenVerify(@RequestBody OauthTokenRequest oauthTokenRequest){
        System.out.println(oauthTokenRequest.getOauthAccessToken());

        return kakaoAuthService.getKakaoUserInfo(oauthTokenRequest);
    }

    @PostMapping("/verify/google")
    public OauthCommonUserInfoDto GoogleAccessTokenVerify(@RequestBody OauthTokenRequest oauthTokenRequest){
        System.out.println(oauthTokenRequest.getOauthAccessToken());

        return kakaoAuthService.getKakaoUserInfo(oauthTokenRequest);
    }


    //TODO : 프로바이더 쿼리 스트링으로 받기 ( 스웨거 끼고 나서 )
    @PostMapping("/register")
    public void registerUser(@RequestBody RegisterRequest registerRequest){
        kakaoAuthService.checkKakaoOauthTokenValid(registerRequest.getOauthAccessToken());
        System.out.println(registerRequest.getOauthAccessToken());

        //회원가입 시켜야함
//        return kakaoAuthService.checkOauthTokenValid(oauthTokenRequest);
    }
    //TODO : 구글 낄때 프로바이더도 인자로받아야함!
    @PostMapping("/login")
    public void loginUser(@RequestBody LoginRequest loginRequest){
        kakaoAuthService.checkKakaoOauthTokenValid(loginRequest.getOauthAccessToken());
//        kakaoAuthService.getKakaoUserInfo()
        //회원가입 시켜야함
//        return kakaoAuthService.checkOauthTokenValid(oauthTokenRequest);
    }
}
