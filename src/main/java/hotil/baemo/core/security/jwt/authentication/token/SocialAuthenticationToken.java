package hotil.baemo.core.security.jwt.authentication.token;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class SocialAuthenticationToken extends UsernamePasswordAuthenticationToken {
    @Getter
    private final String oauth2Id;

    public SocialAuthenticationToken(Object principal, Object credentials) {
        super(principal, principal);
        this.oauth2Id = (String) credentials;
    }
}
