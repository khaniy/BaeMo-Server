package hotil.baemo.domains.score.adapter.output.external;

import hotil.baemo.domains.match.adapter.event.MatchInternalAdapter;
import hotil.baemo.domains.match.adapter.output.persistence.entity.MatchUserEntity;
import hotil.baemo.domains.match.domain.value.match.Team;
import hotil.baemo.domains.score.adapter.output.external.query.ScoreExternalQuery;
import hotil.baemo.domains.score.application.port.output.MatchExternalOutPort;
import hotil.baemo.domains.score.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.score.domain.aggregate.Match;
import hotil.baemo.domains.score.domain.aggregate.MatchUser;
import hotil.baemo.domains.score.domain.value.match.MatchId;
import hotil.baemo.domains.score.domain.value.match.MatchStatus;
import hotil.baemo.domains.score.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchExternalAdapter implements MatchExternalOutPort {

    private final MatchInternalAdapter matchInternalAdapter;
    private final ScoreExternalQuery scoreExternalQuery;
    @Override
    public void updateMatchStatus(MatchId matchId, MatchStatus status) {
        matchInternalAdapter.updateMatchScoring(matchId.id(), status.toString());
    }

    @Override
    public Match getMatch(MatchId matchId) {
        return scoreExternalQuery.findMatch(matchId.id());
    }

    @Override
    public ExerciseUser getExerciseUser(MatchId matchId, UserId userId) {
        return scoreExternalQuery.findExerciseUser(matchId.id(), userId.id());
    }

    @Override
    @CacheEvict(value = "match-detail", key = "'match_users_matchId_'+#matchId.id()")
    public void saveMatchUsers(MatchId matchId, List<MatchUser> matchUsers) {
        List<MatchUserEntity> entities = scoreExternalQuery.findMatchUsers(matchId.id());
        scoreExternalQuery.updateMatchTeamDefined(matchId.id());
        entities.forEach(entity -> {
            matchUsers.stream()
                .filter(matchUser -> matchUser.userId().id().equals(entity.getUserId()))
                .findFirst()
                .ifPresent(matchUser -> {entity.updateTeam(Team.valueOf(matchUser.team().toString()));});
        });
    }
}
