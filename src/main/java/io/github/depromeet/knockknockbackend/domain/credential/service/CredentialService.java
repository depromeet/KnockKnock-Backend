package io.github.depromeet.knockknockbackend.domain.credential.service;

import io.github.depromeet.knockknockbackend.domain.credential.exception.AlreadySignUpUserException;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request.LoginRequest;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request.OauthTokenRequest;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request.RegisterRequest;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.RegisterResponse;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.TokenResponse;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.UserProfileDto;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.VerifyOauthTokenResponse;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.domain.user.domain.repository.UserRepository;
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

     public VerifyOauthTokenResponse getUserInfo(OauthProvider strategy , OauthTokenRequest oauthTokenRequest){

         // oauth 공급자에서 전략 가져온다.
         OauthStrategy oauthStrategy = oauthFactory.getOauthstrategy(strategy.getValue());
         String oauthAccessToken = oauthTokenRequest.getOauthAccessToken();

         // app id 가 같은지 비교 ( 똑똑 앱 어세스토큰인지 확인 )
         oauthStrategy.checkOauthTokenValid(oauthAccessToken);

         // oauth 유저정보 가져오기
         OauthCommonUserInfoDto oauthInfo = oauthStrategy.getUserInfo(oauthAccessToken);

         // 이미 회원가입 되었는지 체크 ( 클라이언트가 없으면 login 쏘고 있으면 reigster 쏘기 )
         boolean isRegistered = userRepository
             .findByOauthIdAndOauthProvider(oauthInfo.getOauthId(), strategy.getValue())
             .isPresent();

         return VerifyOauthTokenResponse.builder()
             .oauthId(oauthInfo.getOauthId())
             .oauthProvider(oauthInfo.getOauthProvider())
             .isRegistered(isRegistered).build();
     }

    public RegisterResponse registerUser(RegisterRequest registerRequest){

        String oauthProviderName = registerRequest.getOauthProvider().getValue();
        String oauthAccessToken = registerRequest.getOauthAccessToken();

        OauthStrategy oauthStrategy = oauthFactory.getOauthstrategy(
            oauthProviderName);
        // app id 가 같은지 비교 ( 똑똑 앱 어세스토큰인지 확인 )
        oauthStrategy.checkOauthTokenValid(oauthAccessToken);

        // oauth 유저정보 가져오기
        OauthCommonUserInfoDto oauthCommonUserInfoDto = oauthStrategy.getUserInfo(
            oauthAccessToken);

        String oauthId = oauthCommonUserInfoDto.getOauthId();
        String nickName = registerRequest.getNickName();
        String email = oauthCommonUserInfoDto.getEmail();

        Optional<User> checkUser = userRepository.findByOauthIdAndOauthProvider(oauthId, oauthProviderName);

        if(checkUser.isPresent())
            throw AlreadySignUpUserException.EXCEPTION;

        //널값있을수 있음 email
        User user = User.builder().oauthProvider("kakao").oauthId(oauthId).nickName(nickName)
            .email(email).build();

        userRepository.save(user);

        return new RegisterResponse("access","refresh",new UserProfileDto(user));
    }


    // 로그인
    public TokenResponse loginUser(LoginRequest loginRequest){
        // 토큰 유효 확인
        // 디비에 저장된 유저인지 확인
            // 저장 안되어있으면 예외처리
        // 로그인
        return null;
    }

    // 리프레쉬 토큰 만들기
    // 레디스 끼기
    // 레디스 ttl
    public void generateRefreshToken(){

    }

    // 토큰 리프레쉬 하기
    public TokenResponse refreshToken(){
     return null;
    }

}
