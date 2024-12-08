package hotil.baemo.domains.clubs.domain.post.value;

import hotil.baemo.domains.clubs.domain.value.post.ClubPostContent;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import hotil.baemo.support.util.BaemoTestEnvironment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ClubPostContentTest extends FixtureMonkeyBaseSupport {

    @RepeatedTest(BaemoTestEnvironment.VALUE_OBJECT_COUNT)
    void 글의_길이는_최소_일_최대_삼만_생성에_성공할_것이다() throws Exception {
        final var clubsPostContent = monkey.giveMeOne(ClubPostContent.class);
        Assertions.assertThat(clubsPostContent).isNotNull();
        Assertions.assertThat(clubsPostContent.content().length()).isLessThanOrEqualTo(30_000);
        Assertions.assertThat(clubsPostContent.content().length()).isGreaterThanOrEqualTo(1);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"  ", ""})
    void 잘못된_매개변수는_생성에_실패할_것이다(final String arg) throws Exception {
        super.assertThrowInvalidArgs(() -> new ClubPostContent(arg));
    }
}