package io.github.depromeet.knockknockbackend.domain.credential.presentation;


import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request.LoginRequest;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request.OauthTokenRequest;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request.RegisterRequest;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.RegisterResponse;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.VerifyOauthTokenResponse;
import io.github.depromeet.knockknockbackend.domain.credential.service.CredentialService;
import io.github.depromeet.knockknockbackend.domain.credential.service.KakaoAuthStrategy;
import io.github.depromeet.knockknockbackend.domain.credential.service.OauthCommonUserInfoDto;
import io.github.depromeet.knockknockbackend.domain.credential.service.OauthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/credentials")
@RequiredArgsConstructor
public class CredentialController {


    private final CredentialService credentialService;


    @PostMapping("/verify/kakao")
    public VerifyOauthTokenResponse kakaoAccessTokenVerify(@RequestBody OauthTokenRequest oauthTokenRequest){
        System.out.println(oauthTokenRequest.getOauthAccessToken());

        return credentialService.getUserInfo(OauthProvider.KAKAO, oauthTokenRequest);
    }

    @PostMapping("/verify/google")
    public VerifyOauthTokenResponse GoogleAccessTokenVerify(@RequestBody OauthTokenRequest oauthTokenRequest){
        return credentialService.getUserInfo(OauthProvider.GOOGLE, oauthTokenRequest);
    }



    @PostMapping("/register")
    public RegisterResponse registerUser(@RequestBody RegisterRequest registerRequest){

        //회원가입 시켜야함
        return credentialService.registerUser(registerRequest);
    }
    @PostMapping("/login")
    public void loginUser(@RequestBody LoginRequest loginRequest){
        //회원가입 시켜야함
//        return kakaoAuthService.checkOauthTokenValid(oauthTokenRequest);
    }
}
