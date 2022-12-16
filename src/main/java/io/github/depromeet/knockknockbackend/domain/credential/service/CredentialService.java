package io.github.depromeet.knockknockbackend.domain.credential.service;

import io.github.depromeet.knockknockbackend.domain.credential.domain.RefreshTokenRedisEntity;
import io.github.depromeet.knockknockbackend.domain.credential.domain.repository.RefreshTokenRedisEntityRepository;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.AfterOauthResponse;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.AuthTokensResponse;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.domain.user.domain.repository.UserRepository;
import io.github.depromeet.knockknockbackend.global.exception.InvalidTokenException;
import io.github.depromeet.knockknockbackend.global.security.JwtTokenProvider;
import java.util.Date;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class CredentialService {


     private final UserRepository userRepository;
     private final OauthFactory oauthFactory;
     private final JwtTokenProvider jwtTokenProvider;

     private final RefreshTokenRedisEntityRepository refreshTokenRedisEntityRepository;

    public String getOauthLink(OauthProvider oauthProvider) {
        OauthStrategy oauthStrategy = oauthFactory.getOauthstrategy(oauthProvider);
        return  oauthStrategy.getOauthLink();
    }
    public AfterOauthResponse oauthCodeToUser(OauthProvider oauthProvider ,String code){
        OauthStrategy oauthStrategy = oauthFactory.getOauthstrategy(oauthProvider);
        String oauthAccessToken = oauthStrategy.getAccessToken(code);
       // 어세스토큰 처음부터 회원가입 테스트 할려면 여기서 얻어야함!
        OauthCommonUserInfoDto oauthUserInfo = oauthStrategy.getUserInfo(oauthAccessToken);

        String oauthId = oauthUserInfo.getOauthId();
        String email = oauthUserInfo.getEmail();

        User user = userRepository.findByOauthIdAndOauthProvider(oauthId, oauthProvider.getValue())
            .orElseGet(()->{
                User newUser = User.builder()
                    .oauthProvider(oauthProvider.getValue())
                    .oauthId(oauthId)
                    .email(email)
                    .build();
                userRepository.save(newUser);
                return newUser;
            });

        Long userId = user.getId();

        Optional<String> nickname = Optional.ofNullable(user.getNickname());

        String accessToken = jwtTokenProvider.generateAccessToken(userId);
        String refreshToken = generateRefreshToken(userId);

        return AfterOauthResponse.builder()
            .isRegistered(nickname.isPresent())
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }


    // 리프레쉬 토큰 만들기
    // 레디스 끼기
    // 레디스 ttl
    private String generateRefreshToken(Long userId){
        String refreshToken = jwtTokenProvider.generateRefreshToken(userId);
        Date tokenExpiredAt = jwtTokenProvider.getTokenExpiredAt(refreshToken);
        RefreshTokenRedisEntity build = RefreshTokenRedisEntity.builder()
            .id(userId.toString())
            .ttl(tokenExpiredAt.getTime())
            .refreshToken(refreshToken)
            .build();
        refreshTokenRedisEntityRepository.save(build);
        return refreshToken;
    }

    // 토큰 리프레쉬 하기
    public AuthTokensResponse tokenRefresh(String requestRefreshToken){
        Long userId = jwtTokenProvider.parseRefreshToken(requestRefreshToken);

        Optional<RefreshTokenRedisEntity> entityOptional = refreshTokenRedisEntityRepository.findByRefreshToken(
            requestRefreshToken);

        RefreshTokenRedisEntity refreshTokenRedisEntity = entityOptional.orElseThrow(
            () -> InvalidTokenException.EXCEPTION);

        if(!userId.toString().equals(refreshTokenRedisEntity.getId())){
            throw InvalidTokenException.EXCEPTION;
        }

        String accessToken = jwtTokenProvider.generateAccessToken(userId);
        String refreshToken = generateRefreshToken(userId);


        return  AuthTokensResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }


}
