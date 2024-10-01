package hotil.baemo.core.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaeMoTimeUtil {
    private static final String ASIA_SEOUL = "Asia/Seoul";

    public static ZonedDateTime convert(Instant instant) {
        return instant.atZone(ZoneId.of(ASIA_SEOUL));
    }
}
