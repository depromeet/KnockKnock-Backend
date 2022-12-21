package io.github.depromeet.knockknockbackend.domain.credential.service;


import io.github.depromeet.knockknockbackend.domain.credential.domain.RefreshTokenRedisEntity;
import io.github.depromeet.knockknockbackend.domain.credential.domain.repository.RefreshTokenRedisEntityRepository;
import io.github.depromeet.knockknockbackend.domain.credential.exception.AlreadySignUpUserException;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request.RegisterRequest;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.AfterOauthResponse;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.AuthTokensResponse;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.AvailableRegisterResponse;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.domain.user.domain.repository.UserRepository;
import io.github.depromeet.knockknockbackend.global.exception.InvalidTokenException;
import io.github.depromeet.knockknockbackend.global.exception.UserNotFoundException;
import io.github.depromeet.knockknockbackend.global.security.JwtTokenProvider;
import io.github.depromeet.knockknockbackend.global.utils.user.UserUtils;
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

    private final UserUtils userUtils;

    public String getOauthLink(OauthProvider oauthProvider) {
        OauthStrategy oauthStrategy = oauthFactory.getOauthstrategy(oauthProvider);
        return oauthStrategy.getOauthLink();
    }

    public AfterOauthResponse oauthCodeToUser(OauthProvider oauthProvider, String code) {
        OauthStrategy oauthStrategy = oauthFactory.getOauthstrategy(oauthProvider);
        String oauthAccessToken = oauthStrategy.getAccessToken(code);
        // 어세스토큰 처음부터 회원가입 테스트 할려면 여기서 얻어야함!
        OauthCommonUserInfoDto oauthUserInfo = oauthStrategy.getUserInfo(oauthAccessToken);

        String oauthId = oauthUserInfo.getOauthId();
        String email = oauthUserInfo.getEmail();

        User user =
                userRepository
                        .findByOauthIdAndOauthProvider(oauthId, oauthProvider.getValue())
                        .orElseGet(
                                () -> {
                                    User newUser =
                                            User.builder()
                                                    .oauthProvider(oauthProvider.getValue())
                                                    .oauthId(oauthId)
                                                    .email(email)
                                                    .build();
                                    userRepository.save(newUser);
                                    return newUser;
                                });

        Long userId = user.getId();

        Optional<String> nickname = Optional.ofNullable(user.getNickname());

        String accessToken = jwtTokenProvider.generateAccessToken(userId, user.getAccountRole());
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
    private String generateRefreshToken(Long userId) {
        String refreshToken = jwtTokenProvider.generateRefreshToken(userId);
        Date tokenExpiredAt = jwtTokenProvider.getTokenExpiredAt(refreshToken);
        RefreshTokenRedisEntity build =
                RefreshTokenRedisEntity.builder()
                        .id(userId.toString())
                        .ttl(tokenExpiredAt.getTime())
                        .refreshToken(refreshToken)
                        .build();
        refreshTokenRedisEntityRepository.save(build);
        return refreshToken;
    }

    // 토큰 리프레쉬 하기
    public AuthTokensResponse tokenRefresh(String requestRefreshToken) {
        Long userId = jwtTokenProvider.parseRefreshToken(requestRefreshToken);

        Optional<RefreshTokenRedisEntity> entityOptional =
                refreshTokenRedisEntityRepository.findByRefreshToken(requestRefreshToken);

        RefreshTokenRedisEntity refreshTokenRedisEntity =
                entityOptional.orElseThrow(() -> InvalidTokenException.EXCEPTION);

        if (!userId.toString().equals(refreshTokenRedisEntity.getId())) {
            throw InvalidTokenException.EXCEPTION;
        }

        User user = userUtils.getUserById(userId);

        String accessToken = jwtTokenProvider.generateAccessToken(userId, user.getAccountRole());
        String refreshToken = generateRefreshToken(userId);

        return AuthTokensResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void validUserCanRegister(
            OIDCDecodePayload oidcDecodePayload, OauthProvider oauthProvider) {
        if (!checkUserCanRegister(oidcDecodePayload, oauthProvider))
            throw AlreadySignUpUserException.EXCEPTION;
    }

    private Boolean checkUserCanRegister(
            OIDCDecodePayload oidcDecodePayload, OauthProvider oauthProvider) {
        Optional<User> user =
                userRepository.findByOauthIdAndOauthProvider(
                        oidcDecodePayload.getSub(), oauthProvider.getValue());
        return user.isEmpty();
    }

    public AvailableRegisterResponse getUserAvailableRegister(
            String token, OauthProvider oauthProvider) {
        OauthStrategy oauthStrategy = oauthFactory.getOauthstrategy(oauthProvider);
        OIDCDecodePayload oidcDecodePayload = oauthStrategy.getOIDCDecodePayload(token);
        Boolean isRegistered = !checkUserCanRegister(oidcDecodePayload, oauthProvider);

        return new AvailableRegisterResponse(isRegistered);
    }

    public AuthTokensResponse registerUserByOCIDToken(
            String token, RegisterRequest registerUserRequest, OauthProvider oauthProvider) {
        OauthStrategy oauthStrategy = oauthFactory.getOauthstrategy(oauthProvider);
        OIDCDecodePayload oidcDecodePayload = oauthStrategy.getOIDCDecodePayload(token);

        validUserCanRegister(oidcDecodePayload, oauthProvider);

        User newUser =
                User.builder()
                        .oauthProvider(oauthProvider.getValue())
                        .oauthId(oidcDecodePayload.getSub())
                        .email(oidcDecodePayload.getEmail())
                        .nickname(registerUserRequest.getNickname())
                        .profilePath(registerUserRequest.getProfilePath())
                        .build();
        userRepository.save(newUser);

        String accessToken =
                jwtTokenProvider.generateAccessToken(newUser.getId(), newUser.getAccountRole());
        String refreshToken = generateRefreshToken(newUser.getId());

        return AuthTokensResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthTokensResponse loginUserByOCIDToken(String token, OauthProvider oauthProvider) {
        OauthStrategy oauthStrategy = oauthFactory.getOauthstrategy(oauthProvider);
        OIDCDecodePayload oidcDecodePayload = oauthStrategy.getOIDCDecodePayload(token);

        User user =
                userRepository
                        .findByOauthIdAndOauthProvider(
                                oidcDecodePayload.getSub(), oauthProvider.getValue())
                        .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        String accessToken =
                jwtTokenProvider.generateAccessToken(user.getId(), user.getAccountRole());
        String refreshToken = generateRefreshToken(user.getId());

        return AuthTokensResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public void deleteUser(String oauthAccessToken) {
        User user = userUtils.getUserFromSecurityContext();
        OauthProvider provider = OauthProvider.valueOf(user.getOauthProvider().toUpperCase());
        OauthStrategy oauthStrategy = oauthFactory.getOauthstrategy(provider);
        OauthCommonUserInfoDto userInfo = oauthStrategy.getUserInfo(oauthAccessToken);
        if (!userInfo.getOauthId().equals(user.getOauthId())) {
            throw InvalidTokenException.EXCEPTION;
        }

        refreshTokenRedisEntityRepository.deleteById(user.getId().toString());
        user.softDeleteUser();
        oauthStrategy.unLink(oauthAccessToken);
    }
}
