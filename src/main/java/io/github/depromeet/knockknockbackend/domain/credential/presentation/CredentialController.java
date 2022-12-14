package io.github.depromeet.knockknockbackend.domain.credential.presentation;


import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request.OauthCodeRequest;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request.RegisterRequest;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request.TokenRefreshRequest;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.AfterOauthResponse;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.AuthTokensResponse;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.AvailableRegisterResponse;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.OauthLoginLinkResponse;
import io.github.depromeet.knockknockbackend.domain.credential.service.CredentialService;
import io.github.depromeet.knockknockbackend.domain.credential.service.OauthProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/credentials")
@RequiredArgsConstructor
@Tag(name = "?????? ?????? ????????????", description = "oauth, token refresh ????????? ???????????????")
public class CredentialController {

    private final CredentialService credentialService;

    @Operation(
            summary = "kakao oauth ????????????",
            description = "kakao ????????? ???????????? ????????????.",
            deprecated = true)
    @GetMapping("/oauth/link/kakao")
    public OauthLoginLinkResponse getKakaoOauthLink() {
        return new OauthLoginLinkResponse(credentialService.getOauthLink(OauthProvider.KAKAO));
    }

    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "???????????? ??????????????? ?????? ????????? ??????",
                        content =
                                @Content(
                                        examples =
                                                @ExampleObject(
                                                        name = "AfterOauthResponse",
                                                        summary = "AfterOauthResponse",
                                                        description =
                                                                "is_registered ?????? false ??? ?????????.",
                                                        value =
                                                                "{\"access_token\":\"string\", \"refresh_token\":\"string\" ,\"is_registered\":false}"))),
                @ApiResponse(
                        responseCode = "200",
                        description = "?????? ??????????????? ?????? ????????? ??????",
                        content =
                                @Content(
                                        schema =
                                                @Schema(implementation = AfterOauthResponse.class)))
            })
    @Operation(summary = "kakao oauth ??????", description = "code??? ????????? ??????????????????.", deprecated = true)
    @GetMapping("/oauth/kakao")
    public AfterOauthResponse kakaoAuth(OauthCodeRequest oauthCodeRequest) {
        // TODO : ???????????? ????????? ???????????? code ???????????? ?????? ?????? ??????.
        return credentialService.oauthCodeToUser(OauthProvider.KAKAO, oauthCodeRequest.getCode());
    }

    @Operation(
            summary = "google oauth ????????????",
            description = "oauth ????????? ???????????? ????????????.",
            deprecated = true)
    @GetMapping("/oauth/link/google")
    public OauthLoginLinkResponse getGoogleOauthLink() {
        return new OauthLoginLinkResponse(credentialService.getOauthLink(OauthProvider.GOOGLE));
    }

    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "???????????? ??????????????? ?????? ????????? ??????",
                        content =
                                @Content(
                                        examples =
                                                @ExampleObject(
                                                        name = "AfterOauthResponse",
                                                        summary = "AfterOauthResponse",
                                                        description =
                                                                "is_registered ?????? false ??? ?????????.",
                                                        value =
                                                                "{\"access_token\":\"string\", \"refresh_token\":\"string\" ,\"is_registered\":false}"))),
                @ApiResponse(
                        responseCode = "200",
                        description = "?????? ??????????????? ?????? ????????? ??????",
                        content =
                                @Content(
                                        schema =
                                                @Schema(implementation = AfterOauthResponse.class)))
            })
    @Operation(summary = "google oauth ??????", description = "code??? ????????? ??????????????????.", deprecated = true)
    @GetMapping("/oauth/google")
    public AfterOauthResponse googleAuth(OauthCodeRequest oauthCodeRequest) {
        return credentialService.oauthCodeToUser(OauthProvider.GOOGLE, oauthCodeRequest.getCode());
    }

    @Operation(summary = "oauth ????????? ?????? ?????????", description = "????????? ???????????? ?????????.")
    @GetMapping("/oauth/valid/register")
    public AvailableRegisterResponse valid(
            @RequestParam("id_token") String token,
            @RequestParam("provider") OauthProvider oauthProvider) {
        return credentialService.getUserAvailableRegister(token, oauthProvider);
    }

    @Operation(summary = "?????? ??????", description = "????????? ???????????? ?????????.")
    @PostMapping("/register")
    public AuthTokensResponse registerUser(
            @RequestParam("id_token") String token,
            @RequestParam("provider") OauthProvider oauthProvider,
            @RequestBody @Valid RegisterRequest registerRequest) {

        return credentialService.registerUserByOCIDToken(token, registerRequest, oauthProvider);
    }

    @Operation(summary = "????????? ??????", description = "?????? ????????? ???????????? ????????? ????????? ?????????")
    @PostMapping("/login")
    public AuthTokensResponse loginUser(
            @RequestParam("id_token") String token,
            @RequestParam("provider") OauthProvider oauthProvider) {
        return credentialService.loginUserByOCIDToken(token, oauthProvider);
    }

    @SecurityRequirement(name = "access-token")
    @Operation(summary = "?????? ????????? ?????????. oauth ????????? unlink ?????????. ")
    @DeleteMapping("/me")
    public void deleteUser(@RequestParam("oauth_access_token") String token) {
        credentialService.deleteUser(token);
    }

    @Operation(summary = "?????? ????????????", description = "????????? ???????????? ?????????.")
    @PostMapping("/refresh")
    public AuthTokensResponse refreshingToken(
            @RequestBody TokenRefreshRequest tokenRefreshRequest) {

        return credentialService.tokenRefresh(tokenRefreshRequest.getRefreshToken());
    }

    @SecurityRequirement(name = "access-token")
    @Operation(summary = "????????????", description = "???????????? ????????? ????????????., ?????? ??????????????????.")
    @PostMapping("/logout")
    public void logout() {
        credentialService.logoutUser();
    }
}
