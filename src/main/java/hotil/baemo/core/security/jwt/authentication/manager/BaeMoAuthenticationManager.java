package hotil.baemo.core.security.jwt.authentication.manager;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.security.jwt.authentication.token.BaeMoAuthenticationToken;
import hotil.baemo.domains.users.adapter.output.persistence.repository.BaeMoUserEntityJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BaeMoAuthenticationManager implements AuthenticationManager {
    private final PasswordEncoder passwordEncoder;
    private final BaeMoUserEntityJpaRepository baeMoUserEntityJpaRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final var token = (BaeMoAuthenticationToken) authentication;

        final var phone = token.getPhone();
        final var password = token.getPassword();

        final var baeMoUserEntity = baeMoUserEntityJpaRepository.loadByPhone(phone);

        if (passwordEncoder.matches(password, baeMoUserEntity.getPassword())) {
            return new BaeMoAuthenticationToken(baeMoUserEntity, password, phone);
        }

        throw new AuthenticationException(ResponseCode.AUTH_FAILED.name()) {
        };
    }
}
