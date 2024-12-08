package hotil.baemo.domains.community.domain.value;

import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import hotil.baemo.support.util.BaemoTestEnvironment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertAll;

class CommunityTitleTest extends FixtureMonkeyBaseSupport {

    @RepeatedTest(BaemoTestEnvironment.VALUE_OBJECT_COUNT)
    void 생성에_성공할_것이다() {
        final var instance = monkey.giveMeOne(CommunityTitle.class);
        assertAll(() -> {
            Assertions.assertThat(instance).isNotNull();
            Assertions.assertThat(instance.title()).isNotBlank();
            Assertions.assertThat(instance.title().length()).isLessThanOrEqualTo(500);
        });
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"   ", ""})
    void 잘못된_매개변수는_생성에_실패할_것이다(final String arg) {
        super.assertThrowInvalidArgs(() -> new CommunityTitle(arg));
    }
}