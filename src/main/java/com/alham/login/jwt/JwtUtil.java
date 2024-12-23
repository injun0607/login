package com.alham.login.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {


    //문자열은 최소 256비트(32글자정도) 이상이여야함
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.access.exp}")
    private String ACCESS_TOKEN_EXPIRE_TIME;

    @Value("${jwt.refresh.exp}")
    private String REFRESH_TOKEN_EXPIRE_TIME;




    private SecretKey getSigningKey() {
        String encoded = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
        byte[] keyBytes = Decoders.BASE64.decode(encoded);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateRefreshToken(String id, String subject) {
        Date now = new Date();
        return Jwts.builder()
                .id(id)
                .subject(subject)
                .issuedAt(now)
                .signWith(getSigningKey())
                .expiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME)) // 7일
                .compact();
    }

    /**
     * JWT 토큰 생성
     *
     * @param id
     * @param subject
     * @return
     */
    public String generateToken(String id, String subject) {
        Date now = new Date();
        return Jwts.builder()
                .id(id)
                .subject(subject)
                .issuedAt(now)
                .signWith(getSigningKey())
                .expiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME)) // 10시간
                .compact();
    }

    public String getSubject(String token) {
        return extractAllClaims(token).getSubject();
    }

    // 토큰 검증
    public Boolean validateToken(String token, String username) {
        try {
            return getSubject(token).equals(username) && !isTokenExpired(token);
        }catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // 내부 메서드: Claims(payload 추출)
    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }
}