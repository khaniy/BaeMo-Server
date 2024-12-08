package hotil.baemo.domains.clubs.domain.comment.value;

import hotil.baemo.domains.clubs.domain.value.comment.RepliesList;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import hotil.baemo.support.util.BaemoTestEnvironment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

class RepliesListTest extends FixtureMonkeyBaseSupport {

    @RepeatedTest(BaemoTestEnvironment.VALUE_OBJECT_COUNT)
    void 생성에_성공할_것이다() throws Exception {
        final var repliesList = monkey.giveMeOne(RepliesList.class);
        Assertions.assertThat(repliesList).isNotNull();
    }
}