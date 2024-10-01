package hotil.baemo.domains.users.domain.policy;

import hotil.baemo.domains.users.domain.value.auth.AuthenticationCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationGenerator {
    private static final int MAX_LENGTH = 6;
    private final RandomWordGenerator randomWordGenerator;

    public AuthenticationCode generate() {
        final var sb = new StringBuilder();
        while (sb.length() < MAX_LENGTH) {
            sb.append(randomWordGenerator.getNumber());
        }

        return new AuthenticationCode(sb.toString());
    }
}