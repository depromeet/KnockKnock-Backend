package io.github.depromeet.knockknockbackend.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .cors().and()
                .csrf().disable();

        http
                .authorizeHttpRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated();

        http
                .apply(new FilterConfig(objectMapper));

        return http.build();
    }

}
