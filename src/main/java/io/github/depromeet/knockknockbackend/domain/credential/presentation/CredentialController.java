package io.github.depromeet.knockknockbackend.domain.credential.presentation;


import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request.OauthCodeRequest;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.OauthLoginLinkResponse;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.UserProfileDto;
import io.github.depromeet.knockknockbackend.domain.credential.service.CredentialService;
import io.github.depromeet.knockknockbackend.domain.credential.service.OauthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/credentials")
@RequiredArgsConstructor
public class CredentialController {


    private final CredentialService credentialService;

    @GetMapping("/oauth/link/kakao")
    public OauthLoginLinkResponse getKakaoOauthLink(){
      return new OauthLoginLinkResponse(credentialService.getOauthLink(OauthProvider.KAKAO));
    }

    @GetMapping("/oauth/kakao")
    public UserProfileDto kakaoAuth(OauthCodeRequest oauthCodeRequest){
        //TODO : 사용자가 로그인 취소시에 code 안넘어옴 별도 처리 필요.
        //TODO : 리프레쉬토큰 , 어세스 토큰 발급
        //TODO : 최초 회원가입 일때 유저 닉네임 업데이트 해야하는 조건

        return credentialService.oauthCodeToUser(OauthProvider.KAKAO, oauthCodeRequest.getCode());
    }


}
