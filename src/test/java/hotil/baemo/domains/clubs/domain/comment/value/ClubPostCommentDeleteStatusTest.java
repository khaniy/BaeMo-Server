package hotil.baemo.domains.clubs.domain.comment.value;

import hotil.baemo.domains.clubs.domain.value.comment.RepliesDeleteStatus;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import hotil.baemo.support.util.BaemoTestEnvironment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

class ClubPostCommentDeleteStatusTest extends FixtureMonkeyBaseSupport {

    @RepeatedTest(BaemoTestEnvironment.VALUE_OBJECT_COUNT)
    void 생성에_성공할_것이다() throws Exception {
        final var repliesDeleteStatus = super.monkey.giveMeOne(RepliesDeleteStatus.class);
        Assertions.assertThat(repliesDeleteStatus).isNotNull();
    }
}