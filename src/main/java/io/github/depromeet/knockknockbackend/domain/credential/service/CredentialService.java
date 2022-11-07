package io.github.depromeet.knockknockbackend.domain.credential.service;

import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request.LoginRequest;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.request.RegisterRequest;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.RegisterResponse;
import io.github.depromeet.knockknockbackend.domain.credential.presentation.dto.response.TokenResponse;

public class CredentialService {



    // 회원가입 시키기
    public RegisterResponse registerUser(RegisterRequest registerRequest){
        OAUTHPROVIDER oauthProvider = registerRequest.getOauthProvider();
        oauthProvider.getOauthProvider();
    }


    // 로그인
    public TokenResponse loginUser(LoginRequest loginRequest){
        // 토큰 유효 확인
        // 디비에 저장된 유저인지 확인
            // 저장 안되어있으면 예외처리
        // 로그인
    }

    // 리프레쉬 토큰 만들기
    // 레디스 끼기
    // 레디스 ttl
    public void generateRefreshToken(){

    }

    // 토큰 리프레쉬 하기
    public TokenResponse refreshToken(){

    }

}
