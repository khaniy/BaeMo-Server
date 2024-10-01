package hotil.baemo.domains.score.adapter.output.memory.repository;

import hotil.baemo.domains.score.adapter.output.memory.dto.ScoreBoardEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ScoreBoardRedisRepository {

    private static final String SCOREBOARD_KEY_PREFIX = "scoreboard:";
    private static final long TIMEOUT = 24;
    private static TimeUnit TIME_UNIT = TimeUnit.HOURS;
    private final RedisTemplate<String, Object> redisTemplate;
    private ValueOperations<String, Object> redis;

    @PostConstruct
    void setRedis() {
        this.redis = redisTemplate.opsForValue();
    }

    public void save(String matchId, ScoreBoardEntity value) {
        String key = getKey(matchId);
        redis.set(key, value, TIMEOUT, TIME_UNIT); // 개별 키에 대해 TTL 설정
    }


    public Optional<ScoreBoardEntity> find(String matchId) {
        String key = getKey(matchId);
        ScoreBoardEntity entity = (ScoreBoardEntity) redis.get(key);
        if (entity == null) {
            return Optional.empty();
        }
        return Optional.of(entity);
    }

    public void delete(String matchId) {
        String key = getKey(matchId);
        redisTemplate.delete(key);
    }

    private static String getKey(String matchId) {
        return SCOREBOARD_KEY_PREFIX + matchId;
    }
}
