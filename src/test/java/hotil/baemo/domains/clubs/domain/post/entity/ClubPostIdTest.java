package hotil.baemo.domains.clubs.domain.post.entity;

import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import hotil.baemo.support.util.BaemoTestEnvironment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ClubPostIdTest extends FixtureMonkeyBaseSupport {

    @RepeatedTest(BaemoTestEnvironment.VALUE_OBJECT_COUNT)
    void 생성에_성공할_것이다() throws Exception {
        final var clubsPostId = monkey.giveMeOne(ClubPostId.class);
        Assertions.assertThat(clubsPostId).isNotNull();
        Assertions.assertThat(clubsPostId.id()).isPositive();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {-1L, -9999L, 0L})
    void 잘못된_매개변수는_생성에_실패할_것이다(final Long arg) throws Exception {
        super.assertThrowInvalidArgs(() -> new ClubPostId(arg));
    }
}