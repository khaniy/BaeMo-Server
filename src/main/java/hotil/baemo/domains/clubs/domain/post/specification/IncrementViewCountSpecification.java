package hotil.baemo.domains.clubs.domain.post.specification;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IncrementViewCountSpecification {
    public static final Duration DEFAULT_TIMEOUT = Duration.ofMinutes(30L);

    public static void spec(ClubsUser clubsUser, ClubsId clubsId) {

    }
}