package io.github.depromeet.knockknockbackend.global.utils.api;


import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import io.github.depromeet.knockknockbackend.global.utils.api.exception.OtherServerBadRequestException;
import io.github.depromeet.knockknockbackend.global.utils.api.exception.OtherServerExpiredTokenException;
import io.github.depromeet.knockknockbackend.global.utils.api.exception.OtherServerForbiddenException;
import io.github.depromeet.knockknockbackend.global.utils.api.exception.OtherServerUnauthorizedException;

public class FeignClientErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		if(response.status() >= 400) {
			switch (response.status()){
				case 401:
					throw OtherServerUnauthorizedException.EXCEPTION;
				case 403:
					throw OtherServerForbiddenException.EXCEPTION;
				case 419:
					throw OtherServerExpiredTokenException.EXCEPTION;
				default:
					throw OtherServerBadRequestException.EXCEPTION;
			}
		}

		return FeignException.errorStatus(methodKey, response);
	}

}
