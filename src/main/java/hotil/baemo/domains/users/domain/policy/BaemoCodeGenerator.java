package hotil.baemo.domains.users.domain.policy;

import hotil.baemo.domains.users.domain.value.entity.BaemoCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaemoCodeGenerator {
    private static final int MAX_LENGTH = 4;
    private final RandomWordGenerator randomWordGenerator;

    public BaemoCode generate() {
        final var sb = new StringBuilder();
        while (sb.length() < MAX_LENGTH) {
            sb.append(randomWordGenerator.getNumber());
        }

        return new BaemoCode(sb.toString());
    }
}