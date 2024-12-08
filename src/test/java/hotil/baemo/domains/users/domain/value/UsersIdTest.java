package hotil.baemo.domains.users.domain.value;

import hotil.baemo.domains.users.domain.value.entity.UsersId;
import hotil.baemo.support.base.ValueObjectTestBaseSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static hotil.baemo.support.util.BaemoTestEnvironment.VALUE_OBJECT_COUNT;
import static org.junit.jupiter.api.Assertions.assertAll;

class UsersIdTest extends ValueObjectTestBaseSupport {

    @RepeatedTest(VALUE_OBJECT_COUNT)
    void 생성에_성공할_것이다() {
        final var instance = super.assertValidArgumentIsNotNull(UsersId.class);
        assertAll(() -> {
            Assertions.assertThat(instance.id()).isNotNull();
            Assertions.assertThat(instance.id()).isPositive();
        });
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {0L, -1L, -9999L})
    void 올바르지_않은_매개변수는_생성에_실패할_것이다(final Long arg) {
        super.assertThatInvalidArgumentThrow(() -> new UsersId(arg));
    }
}