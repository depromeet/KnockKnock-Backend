package io.github.depromeet.knockknockbackend.domain.credential.service;

import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request.OauthCodeRequest;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.TokenResponse;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.UserProfileDto;
import io.github.depromeet.knockknockbackend.domain.user.domain.User;
import io.github.depromeet.knockknockbackend.domain.user.domain.repository.UserRepository;
import io.github.depromeet.knockknockbackend.global.security.JwtTokenProvider;
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

    public String getOauthLink(OauthProvider oauthProvider) {
        OauthStrategy oauthStrategy = oauthFactory.getOauthstrategy(oauthProvider);
        return  oauthStrategy.getOauthLink();
    }
    public UserProfileDto oauthCodeToUser(OauthProvider oauthProvider ,String code){
        OauthStrategy oauthStrategy = oauthFactory.getOauthstrategy(oauthProvider);
        String accessToken = oauthStrategy.getAccessToken(code);
       // 어세스토큰 처음부터 회원가입 테스트 할려면 여기서 얻어야함!
        //        System.out.println(accessToken);
        OauthCommonUserInfoDto oauthUserInfo = oauthStrategy.getUserInfo(accessToken);

        String oauthId = oauthUserInfo.getOauthId();
        String email = oauthUserInfo.getEmail();

        Optional<User> checkUser = userRepository.findByOauthIdAndOauthProvider(oauthId, oauthProvider.getValue());

        if(checkUser.isEmpty()){
            //널값있을수 있음 email
            User user = User.builder().oauthProvider("kakao").oauthId(oauthId).email(email).build();
            userRepository.save(user);
            return new UserProfileDto(user);
        }
        return new UserProfileDto(checkUser.get());
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
