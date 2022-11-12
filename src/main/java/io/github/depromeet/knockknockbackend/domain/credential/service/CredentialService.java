package io.github.depromeet.knockknockbackend.domain.credential.service;

import io.github.depromeet.knockknockbackend.domain.credential.domain.RefreshTokenRedisEntity;
import io.github.depromeet.knockknockbackend.domain.credential.domain.repository.RefreshTokenRedisEntityRepository;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.AfterOauthResponse;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.AccessTokenResponse;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.domain.user.domain.repository.UserRepository;
import io.github.depromeet.knockknockbackend.global.exception.InvalidTokenException;
import io.github.depromeet.knockknockbackend.global.exception.UserNotFoundException;
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
        //        System.out.println(accessToken);
        OauthCommonUserInfoDto oauthUserInfo = oauthStrategy.getUserInfo(oauthAccessToken);

        String oauthId = oauthUserInfo.getOauthId();
        String email = oauthUserInfo.getEmail();

        Optional<User> checkUser = userRepository.findByOauthIdAndOauthProvider(oauthId, oauthProvider.getValue());
        Boolean isRegistered = checkUser.isPresent();
        Long userId ;
        if(!isRegistered){
            //널값있을수 있음 email
            User user = User.builder().oauthProvider(oauthProvider.getValue()).oauthId(oauthId).email(email).build();
            userRepository.save(user);
            userId = user.getId();
        }else {
            userId = checkUser.get().getId();
        }

        String accessToken = jwtTokenProvider.generateAccessToken(userId);
        String refreshToken = generateRefreshToken(userId);

        return AfterOauthResponse.builder()
            .isRegistered(isRegistered)
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
    public AccessTokenResponse tokenRefresh(String refreshToken){
        Long userId = jwtTokenProvider.parseRefreshToken(refreshToken);

        Optional<RefreshTokenRedisEntity> entityOptional = refreshTokenRedisEntityRepository.findByRefreshToken(
            refreshToken);

        RefreshTokenRedisEntity refreshTokenRedisEntity = entityOptional.orElseThrow(
            () -> InvalidTokenException.EXCEPTION);

        if(!userId.toString().equals(refreshTokenRedisEntity.getId())){
            throw InvalidTokenException.EXCEPTION;
        }

        String accessToken = jwtTokenProvider.generateAccessToken(userId);

        return new AccessTokenResponse(accessToken);
    }


}
