package hotil.baemo.config.redis;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
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
    @Getter
    @Value("${spring.profiles.active}")
    private String env;
}

