package io.github.depromeet.knockknockbackend.global.security;

import io.github.depromeet.knockknockbackend.global.exception.ExpiredTokenException;
import io.github.depromeet.knockknockbackend.global.exception.InvalidTokenException;
import io.github.depromeet.knockknockbackend.global.property.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtOIDCProvider {

    private final JwtProperties jwtProperties;

    private final String KID = "kid";

    public String getKidFromTokenHeader(String token) {
        return (String) getUnsignedTokenClaims(getUnsignedToken(token)).getHeader().get(KID);
    }

    private Jwt<Header, Claims> getUnsignedTokenClaims(String unsignedToken) {
        return Jwts.parserBuilder().build().parseClaimsJwt(unsignedToken);
    }

    private String getUnsignedToken(String token) {
        String[] splitToken = token.split("\\.");
        return splitToken[0] + "." + splitToken[1] + ".";
    }

    public Jws<Claims> getOIDCTokenJws(String token, String modulus , String exponent) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(getRSAPublicKey(modulus,exponent))
                .build()
                .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw ExpiredTokenException.EXCEPTION;
        } catch (Exception e) {
            throw InvalidTokenException.EXCEPTION;
        }
    }

    private Key getRSAPublicKey(String modulus , String exponent)
        throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodeN = Base64.getUrlDecoder().decode(modulus);
        byte[] decodeE = Base64.getUrlDecoder().decode(exponent);
        BigInteger n = new BigInteger(1, decodeN);
        BigInteger e = new BigInteger(1, decodeE);

        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(n, e);
        return keyFactory.generatePublic(keySpec);
    }
}
