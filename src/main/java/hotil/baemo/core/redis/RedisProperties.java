package hotil.baemo.core.redis;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({RedisProperties.RedisInfo.class})
public class RedisProperties {
    @ConfigurationProperties(prefix = "spring.data.redis")
    public record RedisInfo(
            String host,
            Integer port,
            String password
    ) {
    }
}

