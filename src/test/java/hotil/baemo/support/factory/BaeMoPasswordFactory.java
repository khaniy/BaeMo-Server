package hotil.baemo.support.factory;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;

import static hotil.baemo.domains.users.domain.specification.BaeMoUsersRegexSpecification.PASSWORD;

public final class BaeMoPasswordFactory {
    private BaeMoPasswordFactory() {
    }

    public static Arbitrary<String> getPasswordArbitrary() {
        return Arbitraries.strings()
            .withChars("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&")
            .ofMinLength(8)
            .ofMaxLength(20)
            .filter(p -> p.matches(PASSWORD));
    }
}