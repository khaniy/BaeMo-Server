package hotil.baemo.domains.users.domain.value.credential;

import hotil.baemo.support.base.ValueObjectTestBaseSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static hotil.baemo.support.util.BaemoTestEnvironment.VALUE_OBJECT_COUNT;
import static org.junit.jupiter.api.Assertions.assertAll;

class PhoneTest extends ValueObjectTestBaseSupport {

    @RepeatedTest(VALUE_OBJECT_COUNT)
    void 생성에_성공할_것이다() {
        final var instance = super.assertValidArgumentIsNotNull(Phone.class);

        assertAll(() -> {
            Assertions.assertThat(instance.phone()).isNotBlank();
            Assertions.assertThat(instance.phone().length()).isEqualTo(11);
            Assertions.assertThat(instance.phone().chars().allMatch(Character::isDigit)).isTrue();
        });
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"  ", ""})
    void 올바르지_않은_매개변수는_생성에_실패할_것이다(final String arg) {
        super.assertThatInvalidArgumentThrow(() -> new Phone(arg));
    }
}