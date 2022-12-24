package io.github.depromeet.knockknockbackend.global.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.depromeet.knockknockbackend.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    private static final String[] SwaggerPatterns = {
        "/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs/**",
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin().disable().cors().and().csrf().disable();

        http.authorizeRequests()
                .expressionHandler(expressionHandler())
                .antMatchers("/api/v1/")
                .permitAll()
                .antMatchers(SwaggerPatterns)
                .permitAll()
                .antMatchers("/actuator/**")
                .permitAll()
                .antMatchers("/api/v1/credentials/me", "/api/v1/credentials/logout")
                .authenticated()
                .antMatchers("/api/v1/credentials/**")
                .permitAll()
                .antMatchers("/api/v1/asset/version")
                .permitAll()
                .antMatchers("/api/v1/asset/profiles/**")
                .permitAll()
                .anyRequest()
                .authenticated();

        http.apply(new FilterConfig(jwtTokenProvider, objectMapper));

        return http.build();
    }

    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
        return roleHierarchy;
    }

    @Bean
    public DefaultWebSecurityExpressionHandler expressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler =
                new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }
}
