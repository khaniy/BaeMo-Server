package hotil.baemo.core.redis;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class BaemoRedis {
    private final StringRedisTemplate redisTemplate;
    private ValueOperations<String, String> redis;

    @PostConstruct
    void setRedis() {
        this.redis = redisTemplate.opsForValue();
    }

    public String get(final Object key) {
        return redis.get(key);
    }

    public void delete(String key) {
        redis.getOperations().delete(key);
    }

    public void set(String key, String value, Duration timeout) {
        redis.set(key, value, timeout);
    }

    public Long getExpiredTime(String key) {
        return redisTemplate.getExpire(key);
    }

    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public boolean notExists(String key) {
        return Boolean.FALSE.equals(redisTemplate.hasKey(key));
    }
}