package io.github.depromeet.knockknockbackend.domain.credential.presentation;


import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request.OauthCodeRequest;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.AfterOauthResponse;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.OauthLoginLinkResponse;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.UserProfileDto;
import io.github.depromeet.knockknockbackend.domain.credential.service.CredentialService;
import io.github.depromeet.knockknockbackend.domain.credential.service.OauthProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/credentials")
@RequiredArgsConstructor
@Tag(name = "인증 관련 컨트롤러", description = "oauth, token refresh 기능을 담당합니다")
public class CredentialController {


    private final CredentialService credentialService;

    @GetMapping("/oauth/link/kakao")
    public OauthLoginLinkResponse getKakaoOauthLink(){
      return new OauthLoginLinkResponse(credentialService.getOauthLink(OauthProvider.KAKAO));
    }

    @GetMapping("/oauth/kakao")
    public ResponseEntity<AfterOauthResponse> kakaoAuth(OauthCodeRequest oauthCodeRequest){
        //TODO : 사용자가 로그인 취소시에 code 안넘어옴 별도 처리 필요.

        AfterOauthResponse afterOauthResponse = credentialService.oauthCodeToUser(OauthProvider.KAKAO, oauthCodeRequest.getCode());
        Boolean isRegistered = afterOauthResponse.getIsRegistered();
        if(isRegistered){
            return ResponseEntity.ok(afterOauthResponse);
        }
        return new ResponseEntity(afterOauthResponse, HttpStatus.CREATED);
    }

    @GetMapping("/oauth/link/google")
    public OauthLoginLinkResponse getGoogleOauthLink(){
        return new OauthLoginLinkResponse(credentialService.getOauthLink(OauthProvider.GOOGLE));
    }

    @GetMapping("/oauth/google")
    public ResponseEntity<AfterOauthResponse> googleAuth(OauthCodeRequest oauthCodeRequest){
        AfterOauthResponse afterOauthResponse = credentialService.oauthCodeToUser(OauthProvider.KAKAO, oauthCodeRequest.getCode());
        Boolean isRegistered = afterOauthResponse.getIsRegistered();
        if(isRegistered){
            return ResponseEntity.ok(afterOauthResponse);
        }
        return new ResponseEntity(afterOauthResponse, HttpStatus.CREATED);
    }


}
