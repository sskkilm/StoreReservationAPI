package zerobase.storereservationapi.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import zerobase.storereservationapi.exception.CustomException;
import zerobase.storereservationapi.service.CustomUserDetailsService;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static zerobase.storereservationapi.type.ErrorCode.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // 1 hour

    private final CustomUserDetailsService customUserDetailsService;

    @Value("${spring.jwt.secret}")
    private String secret;

    /**
     * 토큰 생성 (발급)
     */
    public String generateToken(String username, String role) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        SecretKey secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(now)
                .expiration(expiredDate)
                .signWith(secretKey)
                .compact();
    }

    public Authentication getAuthentication(String jwt) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(this.getUserName(jwt));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserName(String token) {
        SecretKey secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("username", String.class);
    }

    public boolean validateToken(String token) {
        try {
            return !this.parseClaims(token).getExpiration().before(new Date());
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new CustomException(INVALID_TOKEN_SIGNATURE);
        } catch (ExpiredJwtException e) {
            throw new CustomException(EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new CustomException(NOT_SUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new CustomException(INVALID_TOKEN);
        }
    }

    private Claims parseClaims(String token) {
        SecretKey secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
