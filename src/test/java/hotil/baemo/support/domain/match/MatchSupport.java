package hotil.baemo.support.domain.match;

import hotil.baemo.domains.exercise.adapter.output.persist.match.entity.MatchEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.match.repository.MatchRepository;
import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MatchSupport extends FixtureMonkeyBaseSupport {
    @Autowired
    private MatchRepository matchRepository;

    public Long setUpMatch(Long exerciseId, MatchStatus status) {
        MatchEntity entity = matchRepository.save(MatchEntity.builder()
            .exerciseId(exerciseId)
            .matchOrder(1)
            .matchStatus(status)
            .build());
        return entity.getId();
    }

    public Long setUpMatch(Long exerciseId, MatchStatus status, Integer court) {
        MatchEntity entity = matchRepository.save(MatchEntity.builder()
            .exerciseId(exerciseId)
            .matchOrder(1)
            .courtNumber(court)
            .matchStatus(status)
            .build());
        return entity.getId();
    }
}