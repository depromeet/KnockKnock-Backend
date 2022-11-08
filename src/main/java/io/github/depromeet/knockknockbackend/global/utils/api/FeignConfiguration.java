package io.github.depromeet.knockknockbackend.global.utils.api;

import feign.Logger.Level;
import feign.codec.ErrorDecoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableFeignClients(basePackages = "io.github.depromeet.knockknockbackend.global.utils.api")
@Import(FeignClientErrorDecoder.class)
@Configuration
public class FeignConfiguration {

	@Bean
	Level feginLoggerLevel() {
		return Level.BASIC;
	}

	@Bean
	@ConditionalOnMissingBean(value = ErrorDecoder.class)
	public FeignClientErrorDecoder commonFeignErrorDecoder() {
		return new FeignClientErrorDecoder();
	}

}
