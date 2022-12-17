package io.github.depromeet.knockknockbackend.domain.credential.exception;


import io.github.depromeet.knockknockbackend.global.error.exception.ErrorCode;
import io.github.depromeet.knockknockbackend.global.error.exception.KnockException;

// 잘못되거나 appid가 일치하지 않은 인증 토큰일경우 에러
public class OauthTokenInvalidException extends KnockException {
    public static final KnockException EXCEPTION = new OauthTokenInvalidException();

    private OauthTokenInvalidException() {
        super(ErrorCode.EXPIRED_TOKEN);
    }
}
