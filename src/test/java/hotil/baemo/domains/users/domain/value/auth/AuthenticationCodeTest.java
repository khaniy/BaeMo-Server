package hotil.baemo.domains.users.domain.value.auth;

import hotil.baemo.support.base.ValueObjectTestBaseSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static hotil.baemo.support.util.BaemoTestEnvironment.VALUE_OBJECT_COUNT;
import static org.junit.jupiter.api.Assertions.assertAll;

class AuthenticationCodeTest extends ValueObjectTestBaseSupport {

    @RepeatedTest(VALUE_OBJECT_COUNT)
    void 생성에_성공할_것이다() {
        final var instance = super.assertValidArgumentIsNotNull(AuthenticationCode.class);
        assertAll(() -> {
            Assertions.assertThat(instance.code()).isNotBlank();
            Assertions.assertThat(instance.code().length()).isEqualTo(6);
            Assertions.assertThat(instance.code().chars().allMatch(Character::isDigit)).isTrue();
        });
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"  ", ""})
    void 올바르지_않은_매개변수는_생성에_실패할_것이다(final String arg) {
        super.assertThatInvalidArgumentThrow(() -> new AuthenticationCode(arg));
    }
}