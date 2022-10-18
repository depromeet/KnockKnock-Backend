package io.github.depromeet.knockknockbackend.global.security;

import io.github.depromeet.knockknockbackend.global.exception.ExpiredTokenException;
import io.github.depromeet.knockknockbackend.global.exception.InvalidTokenException;
import io.github.depromeet.knockknockbackend.global.property.JwtProperties;
import io.github.depromeet.knockknockbackend.global.security.auth.AuthDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;

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
        String id = getJws(token).getBody().getSubject();
        UserDetails userDetails = authDetailsService.loadUserByUsername(id);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private Jws<Claims> getJws(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSecretKey())
                    .build().parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw ExpiredTokenException.EXCEPTION;
        } catch (Exception e) {
            throw InvalidTokenException.EXCEPTION;
        }
    }

    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }

}
