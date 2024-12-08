package hotil.baemo.support.base;

import hotil.baemo.config.QueryDslConfig;
import hotil.baemo.config.infrastructure.PostgresContainerInitializer;
import hotil.baemo.core.util.BaeMoQueryUtil;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = PostgresContainerInitializer.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({
    QueryDslConfig.class,
    BaeMoQueryUtil.class,
})
@TestPropertySource(properties = {
    "spring.jpa.properties.hibernate.cache.use_second_level_cache=false",
    "spring.jpa.properties.hibernate.cache.use_query_cache=false",
    "spring.datasource.hikari.maximum-pool-size=10",
})
public abstract class RepositoryTestBaseSupport extends FixtureMonkeyBaseSupport {
}