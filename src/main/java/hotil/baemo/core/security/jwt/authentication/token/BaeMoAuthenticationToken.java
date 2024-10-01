package hotil.baemo.core.security.jwt.authentication.token;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class BaeMoAuthenticationToken extends UsernamePasswordAuthenticationToken {
    @Getter
    private final String password;
    @Getter
    private final String phone;

    public BaeMoAuthenticationToken(Object principal, Object credentials, String phone) {
        super(principal, credentials);
        this.password = (String) credentials;
        this.phone = phone;
    }
}