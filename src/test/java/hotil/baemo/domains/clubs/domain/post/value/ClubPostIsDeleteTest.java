package hotil.baemo.domains.clubs.domain.post.value;

import hotil.baemo.domains.clubs.domain.value.post.ClubsPostIsDelete;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import hotil.baemo.support.util.BaemoTestEnvironment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

class ClubPostIsDeleteTest extends FixtureMonkeyBaseSupport {
    @RepeatedTest(BaemoTestEnvironment.VALUE_OBJECT_COUNT)
    void 생성에_성공할_것이다() throws Exception {
        final var clubsPostIsDelete = monkey.giveMeOne(ClubsPostIsDelete.class);
        Assertions.assertThat(clubsPostIsDelete).isNotNull();
    }

    @RepeatedTest(BaemoTestEnvironment.VALUE_OBJECT_COUNT)
    void init_생성에_성공할_것이다() throws Exception {
        Assertions.assertThat(ClubsPostIsDelete.init().isDelete()).isFalse();
    }

    @RepeatedTest(BaemoTestEnvironment.VALUE_OBJECT_COUNT)
    void delete_생성에_성공할_것이다() throws Exception {
        Assertions.assertThat(ClubsPostIsDelete.delete().isDelete()).isTrue();
    }
}