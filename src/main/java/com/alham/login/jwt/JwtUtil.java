package com.alham.login.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    //문자열은 최소 256비트(32글자정도) 이상이여야함
    private final String SECRET_KEY = "AlhamAndSeoHeeisFirstProduction#@!@@";


    private SecretKey getSigningKey() {
        String encoded = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
        byte[] keyBytes = Decoders.BASE64.decode(encoded);
        return Keys.hmacShaKeyFor(keyBytes);
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
                .expiration(new Date(now.getTime() + 1000 * 60 * 60 * 10)) // 10시간
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