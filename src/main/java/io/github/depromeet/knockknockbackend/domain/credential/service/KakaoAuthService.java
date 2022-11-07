package io.github.depromeet.knockknockbackend.domain.credential.service;


import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request.OauthTokenRequest;
import io.github.depromeet.knockknockbackend.domain.credential.service.OauthCommonUserInfoDto.OauthCommonUserInfoDtoBuilder;
import io.github.depromeet.knockknockbackend.domain.user.domain.repository.UserRepository;
import io.github.depromeet.knockknockbackend.global.exception.OauthTokenInvalidException;
import io.github.depromeet.knockknockbackend.global.security.JwtTokenProvider;
import io.github.depromeet.knockknockbackend.global.utils.api.client.KakaoInfoClient;
import io.github.depromeet.knockknockbackend.global.utils.api.dto.response.KakaoAccessTokenInfoResponse;
import io.github.depromeet.knockknockbackend.global.utils.api.dto.response.KakaoInformationResponse;
import io.github.depromeet.knockknockbackend.global.utils.api.dto.response.KakaoInformationResponse.KakaoAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

	private static final String PREFIX = "Bearer ";
	private final KakaoInfoClient kakaoInfoClient;
	private final UserRepository userRepository;

	public OauthCommonUserInfoDto getKakaoUserInfo(OauthTokenRequest oauthTokenRequest) {
		this.checkKakaoOauthTokenValid(oauthTokenRequest.getOauthAccessToken());

		KakaoInformationResponse response = kakaoInfoClient.kakaoUserInfo(PREFIX + oauthTokenRequest.getOauthAccessToken());

		KakaoAccount kakaoAccount = response.getKakaoAccount();
		String oauthId = response.getId();
		OauthCommonUserInfoDtoBuilder oauthCommonUserInfoDtoBuilder = OauthCommonUserInfoDto.builder()
			.oauthProvider("kakao")
			.oauthId(oauthId);
		// 계정정보가 널이 아니면..!
		if(kakaoAccount != null){
			String email = kakaoAccount.getEmail();
			if(email != null){
				oauthCommonUserInfoDtoBuilder.email(email);
			}
		}
		boolean isRegistered = userRepository.findByOauthIdAndOauthProvider(oauthId, "kakao").isPresent();
		oauthCommonUserInfoDtoBuilder.isRegistered(isRegistered);

		return oauthCommonUserInfoDtoBuilder.build();
	}

	public void checkKakaoOauthTokenValid(String oauthAccessToken){
		KakaoAccessTokenInfoResponse response = kakaoInfoClient.kakaoAccessTokenInfo(PREFIX + oauthAccessToken);


		//TODO : 앱 아이디 환경변수에서 받아와서 바꾸삼!
		if(!response.getAppId().equals("820048"))
			throw OauthTokenInvalidException.EXCEPTION;

	}

}
