package hotil.baemo.support.base;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import org.assertj.core.api.ThrowableAssert;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public abstract class ValueObjectTestBaseSupport extends FixtureMonkeyBaseSupport {

    protected final <T> T assertValidArgumentIsNotNull(final Class<T> clazz) {
        final T instance = monkey.giveMeOne(clazz);
        assertThat(instance).isNotNull();

        return instance;
    }

    protected final void assertAllNotThrow(final ThrowableAssert.ThrowingCallable... executables) {
        assertAll(Stream.of(executables)
            .map(newInstance ->
                () -> assertThatCode(newInstance).doesNotThrowAnyException()));
    }

    protected final void assertThatInvalidArgumentThrow(final ThrowableAssert.ThrowingCallable invalidArgument) {
        assertThatThrownBy(invalidArgument)
            .isInstanceOf(CustomException.class)
            .hasMessage(ResponseCode.INVALID_VALUE.name());
    }
}