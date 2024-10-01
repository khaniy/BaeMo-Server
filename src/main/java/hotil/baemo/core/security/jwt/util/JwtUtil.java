package hotil.baemo.core.security.jwt.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final static String AUTHORIZATION = "Authorization";

    private static final String BLACK_LIST = "BLACKLISTED";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String USER_NAME = "id";
    private static final Class<Long> USER_IDX_TYPE = Long.class;
    private static final String USER_ROLE = "role";
    private static final Class<String> USER_ROLE_TYPE = String.class;

    private final JwtProperties jwtProperties;

    private final StringRedisTemplate redis;

    public String parseToken(final String token) {
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            return token.substring(TOKEN_PREFIX.length());
        }

        return null;
    }

    public Long getUserIdx(final String token) throws JwtException, IllegalArgumentException {

        return this.getClaims(token)
            .get(USER_NAME, USER_IDX_TYPE);
    }

    public Claims getClaims(final String token) throws JwtException, IllegalArgumentException {

        return Jwts.parser()
            .verifyWith(jwtProperties.getSecretKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public String generateAccessToken(final Long userIdx, final String role) {
        final Instant now = Instant.now();

        final String token = Jwts.builder()
            .claim(USER_NAME, userIdx)
            .claim(USER_ROLE, role)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plusSeconds(jwtProperties.getExpired())))
            .signWith(jwtProperties.getSecretKey())
            .compact();

        return TOKEN_PREFIX + token;
    }

    public void removeToken(String token) {
        redis.opsForValue()
            .set(token, BLACK_LIST, Duration.ofMillis(jwtProperties.getExpired()));
    }

    public boolean isValidToken(final String token) {
        try {
            final var claims = this.getClaims(token);
            return token != null
                && checkRemoved(token)
                && checkExpired(claims)
                ;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkExpired(Claims claims) {
        return claims.getExpiration()
            .after(new Date());
    }

    private boolean checkRemoved(String token) {
        return redis.opsForValue().get(token) == null;
    }

    public String getTokenKey() {
        return AUTHORIZATION;
    }
}