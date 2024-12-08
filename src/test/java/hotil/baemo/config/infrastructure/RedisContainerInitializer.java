package hotil.baemo.config.infrastructure;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.Map;

@Testcontainers
public class RedisContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final String REDIS_IMAGE = "redis:latest";
    private static final Integer REDIS_PORT = 6379;
    private static final String REDIS_PASSWORD = "lkdcode";

    @Container
    public static final GenericContainer<?> REDIS_CONTAINER;

    static {
        REDIS_CONTAINER = new GenericContainer<>(REDIS_IMAGE)
            .withExposedPorts(REDIS_PORT)
            .withEnv("REDIS_PASSWORD", REDIS_PASSWORD);
        REDIS_CONTAINER.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        final var environment = applicationContext.getEnvironment();
        final Map<String, Object> testContainersProperties = new HashMap<>();
        testContainersProperties.put("spring.data.redis.host", REDIS_CONTAINER.getHost());
        testContainersProperties.put("spring.data.redis.port", REDIS_CONTAINER.getMappedPort(REDIS_PORT));
        testContainersProperties.put("spring.data.redis.password", REDIS_PASSWORD);
        environment.getPropertySources().addFirst(new MapPropertySource("testcontainers-redis", testContainersProperties));
    }
}