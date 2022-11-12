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

    private String buildToken(Long id, Date issuedAt, Date accessTokenExpiresIn,String claimValue) {
        final Key encodedKey = getSecretKey();
        return Jwts.builder()
            .setIssuer("knockknock")
            .setIssuedAt(issuedAt)
            .setSubject(id.toString())
            .claim("type", claimValue)
            .setExpiration(accessTokenExpiresIn)
            .signWith(encodedKey)
            .compact();
    }

    public String generateAccessToken(Long id) {
        final Date issuedAt = new Date();
        final Date accessTokenExpiresIn = new Date(issuedAt.getTime() + jwtProperties.getAccessExp());

        return buildToken(id, issuedAt, accessTokenExpiresIn,"access_token");
    }


    //TODO : 리프레쉬 토큰 생성후에 레디스 저장하는 로직 필요 ( credential 서비스에..? )
    public String generateRefreshToken(Long id) {
        final Date issuedAt = new Date();
        final Date refreshTokenExpiresIn = new Date(issuedAt.getTime() + jwtProperties.getRefreshExp());
        return buildToken(id, issuedAt, refreshTokenExpiresIn,"refresh_token");
    }

    public boolean isAccessToken(String token) {
        return getJws(token).getBody().get("type").equals("access_token");
    }

    public boolean isRefreshToken(String token) {
        return getJws(token).getBody().get("type").equals("refresh_token");
    }

    public Long parseAccessToken(String token) {
        if (isAccessToken(token)) {
            Claims claims = getJws(token).getBody();
            return Long.parseLong(claims.getSubject());
        }
        throw InvalidTokenException.EXCEPTION;
    }

    public Long parseRefreshToken(String token) {
        if (isRefreshToken(token)) {
            Claims claims = getJws(token).getBody();
            return Long.parseLong(claims.getSubject());
        }
        throw InvalidTokenException.EXCEPTION;
    }

    public Date getTokenExpiredAt(String token){
        return getJws(token).getBody().getExpiration();
    }

}
