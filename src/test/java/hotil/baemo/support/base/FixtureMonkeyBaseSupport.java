package hotil.baemo.support.base;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FailoverIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;

import java.util.List;

public abstract class FixtureMonkeyBaseSupport {
    protected final FixtureMonkey monkey = FixtureMonkey.builder()
            .objectIntrospector(new FailoverIntrospector(List.of(
                    FieldReflectionArbitraryIntrospector.INSTANCE,
                    ConstructorPropertiesArbitraryIntrospector.INSTANCE,
                    BuilderArbitraryIntrospector.INSTANCE
            )))
            .plugin(new JakartaValidationPlugin())
            .defaultNotNull(true)
            .build();

    protected void assertThrowInvalidArgs(final ThrowableAssert.ThrowingCallable throwingCallable) {
        Assertions.assertThatThrownBy(throwingCallable)
                .isInstanceOf(CustomException.class)
                .hasMessage(ResponseCode.INVALID_VALUE.name());
    }
}