package hotil.baemo.domains.users.domain.policy;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
class RandomWordGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int NUMBER_BOUND = 10;

    public String getNumber() {
        return String.valueOf(RANDOM.nextInt(NUMBER_BOUND));
    }
}
