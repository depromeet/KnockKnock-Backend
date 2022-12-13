package io.github.depromeet.knockknockbackend.domain.credential.presentation;


import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request.OauthCodeRequest;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request.TokenRefreshRequest;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.AfterOauthResponse;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.AuthTokensResponse;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.OauthLoginLinkResponse;
import io.github.depromeet.knockknockbackend.domain.credential.service.CredentialService;
import io.github.depromeet.knockknockbackend.domain.credential.service.OauthProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/credentials")
@RequiredArgsConstructor
@Tag(name = "인증 관련 컨트롤러", description = "oauth, token refresh 기능을 담당합니다")
public class CredentialController {


    private final CredentialService credentialService;


    @Operation(summary = "kakao oauth 링크발급", description = "kakao 링크를 받아볼수 있습니다.")
    @GetMapping("/oauth/link/kakao")
    public OauthLoginLinkResponse getKakaoOauthLink(){
      return new OauthLoginLinkResponse(credentialService.getOauthLink(OauthProvider.KAKAO));
    }


    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "이전까지 회원가입을 하지 않았던 경우",
            content = @Content(examples =  @ExampleObject(name = "AfterOauthResponse",
                summary = "AfterOauthResponse",
                description = "is_registered 값이 false 로 옵니다.",
                value = "{\"access_token\":\"string\", \"refresh_token\":\"string\" ,\"is_registered\":false}"))),
        @ApiResponse(responseCode = "200", description = "이미 회원가입을 했던 유저인 경우",
            content = @Content(schema = @Schema(implementation = AfterOauthResponse.class)))})
    @Operation(summary = "kakao oauth 요청", description = "code를 서버로 요청보냅니다.")
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


    @Operation(summary = "google oauth 링크발급", description = "oauth 링크를 받아볼수 있습니다.")
    @GetMapping("/oauth/link/google")
    public OauthLoginLinkResponse getGoogleOauthLink(){
        return new OauthLoginLinkResponse(credentialService.getOauthLink(OauthProvider.GOOGLE));
    }


    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "이전까지 회원가입을 하지 않았던 경우",
            content = @Content(examples =  @ExampleObject(name = "AfterOauthResponse",
                summary = "AfterOauthResponse",
                description = "is_registered 값이 false 로 옵니다.",
                value = "{\"access_token\":\"string\", \"refresh_token\":\"string\" ,\"is_registered\":false}"))),
        @ApiResponse(responseCode = "200", description = "이미 회원가입을 했던 유저인 경우",
            content = @Content(schema = @Schema(implementation = AfterOauthResponse.class)))})
    @Operation(summary = "google oauth 요청", description = "code를 서버로 요청보냅니다.")
    @GetMapping("/oauth/google")
    public ResponseEntity<AfterOauthResponse> googleAuth(OauthCodeRequest oauthCodeRequest){
        AfterOauthResponse afterOauthResponse = credentialService.oauthCodeToUser(OauthProvider.GOOGLE, oauthCodeRequest.getCode());
        Boolean isRegistered = afterOauthResponse.getIsRegistered();
        if(isRegistered){
            return ResponseEntity.ok(afterOauthResponse);
        }
        return new ResponseEntity(afterOauthResponse, HttpStatus.CREATED);
    }


    @Operation(summary = "토큰 리프레쉬", description = "토큰을 리프레쉬 합니다.")
    @PostMapping("/refresh")
    public AuthTokensResponse refreshingToken(@RequestBody TokenRefreshRequest tokenRefreshRequest){
        AuthTokensResponse authTokensResponse = credentialService.tokenRefresh(
            tokenRefreshRequest.getRefreshToken());
        return authTokensResponse;
    }


}
