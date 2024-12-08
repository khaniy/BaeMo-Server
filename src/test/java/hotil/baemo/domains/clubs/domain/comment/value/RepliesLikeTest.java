package hotil.baemo.domains.clubs.domain.comment.value;

import hotil.baemo.domains.clubs.domain.value.comment.RepliesLike;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import hotil.baemo.support.util.BaemoTestEnvironment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

class RepliesLikeTest extends FixtureMonkeyBaseSupport {

    @RepeatedTest(BaemoTestEnvironment.VALUE_OBJECT_COUNT)
    void 생성에_성공할_것이다() throws Exception {
        final var repliesLike = super.monkey.giveMeOne(RepliesLike.class);
        Assertions.assertThat(repliesLike).isNotNull();
    }

    @ParameterizedTest
    @NullSource
    void 잘못된_매개변수는_생성에_실패할_것이다(final Boolean arg) {
        super.assertThrowInvalidArgs(() -> new RepliesLike(arg));
    }
}