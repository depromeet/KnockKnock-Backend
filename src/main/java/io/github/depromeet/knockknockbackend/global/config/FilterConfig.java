package io.github.depromeet.knockknockbackend.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.depromeet.knockknockbackend.global.error.ExceptionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class FilterConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final ObjectMapper objectMapper;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        ExceptionFilter exceptionFilter = new ExceptionFilter(objectMapper);
        //TODO JwtFilter
        builder.addFilterBefore(exceptionFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
