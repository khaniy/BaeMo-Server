package hotil.baemo.domains.clubs.domain.post.value;

import hotil.baemo.domains.clubs.domain.value.post.ClubPostTitle;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import hotil.baemo.support.util.BaemoTestEnvironment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ClubPostTitleTest extends FixtureMonkeyBaseSupport {
    @RepeatedTest(BaemoTestEnvironment.VALUE_OBJECT_COUNT)
    void 최소_글자_수_일_최대_글자_수_이백_생성에_성공할_것이다() throws Exception {
        final var clubsPostTitle = monkey.giveMeOne(ClubPostTitle.class);
        Assertions.assertThat(clubsPostTitle).isNotNull();
        Assertions.assertThat(clubsPostTitle.title().length()).isGreaterThanOrEqualTo(1);
        Assertions.assertThat(clubsPostTitle.title().length()).isLessThanOrEqualTo(200);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"    ", ""})
    void 잘못된_매개변수는_생성에_실패할_것이다(final String arg) throws Exception {
        super.assertThrowInvalidArgs(() -> new ClubPostTitle(arg));
    }
}