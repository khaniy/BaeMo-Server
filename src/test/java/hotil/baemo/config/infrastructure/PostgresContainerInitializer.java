package hotil.baemo.config.infrastructure;


import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.Map;

@Testcontainers
public class PostgresContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final String POSTGRE_IMAGE = "postgres:latest";
    private static final String POSTGRE_DATABASE_NAME = "users";
    private static final String POSTGRE_USER_NAME = "postgres";
    private static final String POSTGRE_PASSWORD = "lkdcode";

    @Container
    public static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER;

    static {
        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>(POSTGRE_IMAGE)
            .withDatabaseName(POSTGRE_DATABASE_NAME)
            .withUsername(POSTGRE_USER_NAME)
            .withPassword(POSTGRE_PASSWORD)
            .withReuse(false);
        POSTGRE_SQL_CONTAINER.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        final var environment = applicationContext.getEnvironment();
        final Map<String, Object> testContainersProperties = new HashMap<>();
        testContainersProperties.put("spring.datasource.url", POSTGRE_SQL_CONTAINER.getJdbcUrl());
        testContainersProperties.put("spring.datasource.username", POSTGRE_SQL_CONTAINER.getUsername());
        testContainersProperties.put("spring.datasource.password", POSTGRE_SQL_CONTAINER.getPassword());
        environment.getPropertySources().addFirst(new MapPropertySource("testcontainers-postgres", testContainersProperties));
    }
}