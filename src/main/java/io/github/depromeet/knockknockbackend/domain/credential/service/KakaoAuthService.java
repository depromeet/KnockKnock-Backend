package io.github.depromeet.knockknockbackend.domain.credential.service;


import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request.OauthTokenRequest;
import io.github.depromeet.knockknockbackend.global.utils.api.client.KakaoInfoClient;
import io.github.depromeet.knockknockbackend.global.utils.api.dto.response.KakaoInformationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

	private static final String PREFIX = "Bearer ";
	private final KakaoInfoClient kakaoInfoClient;
//	private final UserRepository userRepository;
//	private final JwtTokenProvider jwtTokenProvider;

	public void checkOauthTokenValid(OauthTokenRequest oauthTokenRequest) {

		KakaoInformationResponse response = kakaoInfoClient.kakaoInfo(PREFIX + oauthTokenRequest.getOauthAccessToken());

		System.out.println(response.getKakaoAccount().getEmail());
//		if(userRepository.findById(response.getEmail()).isEmpty()) {
//			userRepository.save(
//					User.builder()
//					.id(response.getEmail())
//					.name(response.getName())
//					.profileImage(response.getProfileImage())
//					.build()
//			);
//		}

//		return new TokenResponse(jwtTokenProvider.generateAccessToken(response.getEmail()));
	}

}
