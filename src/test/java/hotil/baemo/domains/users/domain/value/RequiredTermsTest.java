package hotil.baemo.domains.users.domain.value;

import hotil.baemo.domains.users.domain.value.terms.RequiredTerms;
import hotil.baemo.support.base.ValueObjectTestBaseSupport;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static hotil.baemo.support.util.BaemoTestEnvironment.VALUE_OBJECT_COUNT;
import static org.junit.jupiter.api.Assertions.assertAll;

class RequiredTermsTest extends ValueObjectTestBaseSupport {

    @RepeatedTest(VALUE_OBJECT_COUNT)
    void 생성에_성공할_것이다() {
        final var instance = super.assertValidArgumentIsNotNull(RequiredTerms.class);
        assertAll(() -> {
        });
    }

    @ParameterizedTest
    @NullSource
    void 올바르지_않은_매개변수는_생성에_실패할_것이다(final Boolean arg) {
        super.assertThatInvalidArgumentThrow(() -> new RequiredTerms(arg));
    }
}