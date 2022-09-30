package io.github.depromeet.knockknockbackend.global.security;

import io.github.depromeet.knockknockbackend.global.property.JwtProperties;
import io.github.depromeet.knockknockbackend.global.security.auth.AuthDetails;
import io.github.depromeet.knockknockbackend.global.security.auth.AuthDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final AuthDetailsService authDetailsService;

    public String resolveToken(HttpServletRequest request) {
        String rawHeader = request.getHeader(jwtProperties.getHeader());

        if(rawHeader != null && rawHeader.length() > jwtProperties.getPrefix().length() &&
                rawHeader.startsWith(jwtProperties.getPrefix())) {
            return rawHeader.substring(jwtProperties.getPrefix().length() + 1);
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        String id = token; // TODO token에서 id 가져오기.
        UserDetails userDetails = authDetailsService.loadUserByUsername(id);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
