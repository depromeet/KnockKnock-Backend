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
import java.util.Date;
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

    public String generateAccessToken(Long id) {
        final Key encodedKey = getSecretKey();
        final Date now = new Date();
        final Date accessTokenExpiresIn = new Date(now.getTime() + jwtProperties.getAccessExp());
        return Jwts.builder()
            .setIssuer("knockknock")
            .setIssuedAt(now)
            .setSubject(id.toString())
            .claim("type", "access_token")
            .setExpiration(accessTokenExpiresIn)
            .signWith(encodedKey)
            .compact();
    }


    //TODO : 리프레쉬 토큰 생성후에 레디스 저장하는 로직 필요 ( credential 서비스에..? )
    public String generateRefreshToken(Long id) {
        final Key encodedKey = getSecretKey();
        final Date now = new Date();
        final Date refreshTokenExpiresIn = new Date(now.getTime() + jwtProperties.getRefreshExp());
        return Jwts.builder()
            .setIssuer("knockknock")
            .setIssuedAt(now)
            .setSubject(id.toString())
            .claim("type", "refresh_token")
            .setExpiration(refreshTokenExpiresIn)
            .signWith(encodedKey)
            .compact();
    }

    public boolean isAccessToken(String token) {
        return getJws(token).getBody().get("type").equals("refresh_token");
    }

    public boolean isRefreshToken(String token) {
        return getJws(token).getBody().get("type").equals("refresh_token");
    }

    public Long parseAccessToken(String token) {
        Claims claims = getJws(token).getBody();
        if (claims.get("type").equals("access_token")) {
            return Long.parseLong(claims.getSubject());
        }
        throw InvalidTokenException.EXCEPTION;
    }

    public Long parseRefreshToken(String token) {
        Claims claims = getJws(token).getBody();
        if (claims.get("type").equals("refresh_token")) {
            return Long.parseLong(claims.getSubject());
        }
        throw InvalidTokenException.EXCEPTION;
    }

}
