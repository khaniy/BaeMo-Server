package hotil.baemo.domains.community.domain.entity;

import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import hotil.baemo.support.util.BaemoTestEnvironment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertAll;

class WriterTest extends FixtureMonkeyBaseSupport {

    @RepeatedTest(BaemoTestEnvironment.VALUE_OBJECT_COUNT)
    void 생성에_성공할_것이다() {
        final var instance = monkey.giveMeOne(Writer.class);
        assertAll(() -> {
            Assertions.assertThat(instance).isNotNull();
            Assertions.assertThat(instance.id()).isGreaterThanOrEqualTo(1L);
        });
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {0L, -1L, -9999L})
    void 올바르지_않은_매개변수는_생성에_실패할_것이다(final Long arg) {
        Assertions.assertThatThrownBy(() -> new Writer(arg))
            .isInstanceOf(CustomException.class);
    }
}