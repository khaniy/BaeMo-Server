package hotil.baemo.domains.clubs.domain.comment.value;

import hotil.baemo.domains.clubs.domain.value.comment.CommentContent;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import hotil.baemo.support.util.BaemoTestEnvironment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ClubPostCommentContentTest extends FixtureMonkeyBaseSupport {

    @RepeatedTest(BaemoTestEnvironment.VALUE_OBJECT_COUNT)
    void 생성에_성공할_것이다() throws Exception {
        final var repliesContent = super.monkey.giveMeOne(CommentContent.class);
        Assertions.assertThat(repliesContent).isNotNull();
        Assertions.assertThat(repliesContent.content().length()).isLessThanOrEqualTo(1_000);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "    "})
    void 잘못된_매개변수는_생성에_실패할_것이다(final String arg) throws Exception {
        super.assertThrowInvalidArgs(() -> new CommentContent(arg));
    }
}