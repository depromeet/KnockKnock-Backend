package io.github.depromeet.knockknockbackend.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.depromeet.knockknockbackend.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    private static final String[] SwaggerPatterns = {
        "/swagger-resources/**",
        "/swagger-ui/**",
        "/v3/api-docs/**",
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .cors().and()
                .csrf().disable();

        http
                .authorizeHttpRequests()
                .antMatchers("/").permitAll()
                .antMatchers(SwaggerPatterns).permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/credentials/**").permitAll()
                .antMatchers("/asset/version").permitAll()
                .anyRequest().authenticated();
        http
            .apply(new FilterConfig(jwtTokenProvider, objectMapper));

        return http.build();
    }

}
