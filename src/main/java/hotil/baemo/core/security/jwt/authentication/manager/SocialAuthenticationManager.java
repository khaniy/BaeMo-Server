package hotil.baemo.core.security.jwt.authentication.manager;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.security.jwt.authentication.token.SocialAuthenticationToken;
import hotil.baemo.domains.users.adapter.output.persistence.repository.SocialJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SocialAuthenticationManager implements AuthenticationManager {
    private final SocialJpaRepository socialJpaRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final var token = (SocialAuthenticationToken) authentication;
        final var oauth2Id = token.getOauth2Id();

        final var socialUserEntity = socialJpaRepository.loadByOauthId(oauth2Id);

        if (Objects.equals(socialUserEntity.getOauthId(), oauth2Id)) {
            return new SocialAuthenticationToken(socialUserEntity, oauth2Id);
        }

        throw new AuthenticationException(ResponseCode.AUTH_FAILED.name()) {
        };
    }
}