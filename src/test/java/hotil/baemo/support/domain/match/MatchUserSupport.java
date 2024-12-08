package hotil.baemo.support.domain.match;

import hotil.baemo.domains.exercise.adapter.output.persist.match.entity.MatchUserEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.match.repository.MatchUserRepository;
import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
import hotil.baemo.domains.exercise.domain.value.match.Team;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class MatchUserSupport extends FixtureMonkeyBaseSupport {
    @Autowired
    private MatchUserRepository matchUserRepository;

    public void setMatchUsers(Long exerciseId,Long matchId, List<Long> userIds) {
        List<MatchUserEntity> entities = userIds.stream()
            .map(i -> MatchUserEntity.builder()
                .exerciseId(exerciseId)
                .userId(i)
                .matchId(matchId)
                .team(Team.UNDEFINED)
                .matchStatus(MatchStatus.WAITING)
                .build())
            .collect(Collectors.toList());
        matchUserRepository.saveAll(entities);
    }
}