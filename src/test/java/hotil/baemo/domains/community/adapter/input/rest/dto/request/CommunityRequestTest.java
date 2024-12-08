package hotil.baemo.domains.community.adapter.input.rest.dto.request;

import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import hotil.baemo.support.util.BaemoTestEnvironment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertAll;

class CommunityRequestTest extends FixtureMonkeyBaseSupport {

    @RepeatedTest(BaemoTestEnvironment.VALUE_OBJECT_COUNT)
    void create_dto_생성에_성공할_것이다() {
        final var instance = monkey.giveMeBuilder(CommunityRequest.CreateDTO.class).sample();

        assertAll(() -> {
            Assertions.assertThat(instance.title().length()).isLessThanOrEqualTo(500);
            Assertions.assertThat(instance.title()).isNotBlank();
            Assertions.assertThat(instance.content().length()).isLessThanOrEqualTo(3_000);
            Assertions.assertThat(instance.content()).isNotBlank();
            Assertions.assertThat(instance.category()).isNotNull();
        });
    }
}