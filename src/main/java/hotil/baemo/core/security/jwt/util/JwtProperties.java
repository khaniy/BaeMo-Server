package hotil.baemo.core.security.jwt.util;


import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtProperties {
    @Getter
    @Value("${app.jwt.issuer}")
    private String issuer;
    @Value("${app.jwt.secret_key}")
    private String secretKey;
    @Value("${app.jwt.expired}")
    private String expired;

    public SecretKey getSecretKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Long getExpired() {
        return Long.parseLong(expired);
    }
}