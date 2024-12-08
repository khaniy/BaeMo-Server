package hotil.baemo.domains.users.domain.value.information;

import hotil.baemo.domains.users.domain.value.entity.BaemoCode;
import hotil.baemo.support.base.ValueObjectTestBaseSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static hotil.baemo.support.util.BaemoTestEnvironment.VALUE_OBJECT_COUNT;
import static org.junit.jupiter.api.Assertions.assertAll;

class BaemoCodeTest extends ValueObjectTestBaseSupport {

    @RepeatedTest(VALUE_OBJECT_COUNT)
    void 생성에_성공할_것이다() {
        final var instance = super.assertValidArgumentIsNotNull(BaemoCode.class);
        assertAll(() -> {
            Assertions.assertThat(instance.code()).isNotBlank();
            Assertions.assertThat(instance.code().length()).isEqualTo(4);
            Assertions.assertThat(instance.code().chars().allMatch(Character::isDigit)).isTrue();
        });
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"  ", "", "abcd", "가나다라", "123", "12345", "!@#$"})
    void 올바르지_않은_매개변수는_생성에_실패할_것이다(final String arg) {
        super.assertThatInvalidArgumentThrow(() -> new BaemoCode(arg));
    }
}