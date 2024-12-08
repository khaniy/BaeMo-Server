package hotil.baemo.domains.users.domain.value.information;

import hotil.baemo.support.base.ValueObjectTestBaseSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static hotil.baemo.support.util.BaemoTestEnvironment.VALUE_OBJECT_COUNT;
import static org.junit.jupiter.api.Assertions.assertAll;

class BirthTest extends ValueObjectTestBaseSupport {

    @RepeatedTest(VALUE_OBJECT_COUNT)
    void 생성에_성공할_것이다() {
        final var instance = super.assertValidArgumentIsNotNull(Birth.class);
        assertAll(() -> {
            Assertions.assertThat(instance.birth()).isNotNull();
        });
    }

    @ParameterizedTest
    @NullSource
    @MethodSource(value = "invalid")
    void 올바르지_않은_매개변수는_생성에_실패할_것이다(final LocalDate arg) {
        super.assertThatInvalidArgumentThrow(() -> new Birth(arg));
    }

    private static Stream<Arguments> invalid() {
        return Stream.of(
            Arguments.of(LocalDate.now().plusDays(1)),
            Arguments.of(LocalDate.now().plusMonths(1))
        );
    }
}