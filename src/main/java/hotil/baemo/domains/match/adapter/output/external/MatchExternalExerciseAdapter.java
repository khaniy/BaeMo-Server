package hotil.baemo.domains.match.adapter.output.external;

import hotil.baemo.domains.exercise.adapter.output.persist.entity.ExerciseUserEntity;
import hotil.baemo.domains.match.adapter.output.external.query.MatchExternalQuery;
import hotil.baemo.domains.match.application.port.output.ExerciseExternalOutPort;
import hotil.baemo.domains.match.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.match.domain.aggregate.Match;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseUserRole;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseUserStatus;
import hotil.baemo.domains.match.domain.value.match.MatchId;
import hotil.baemo.domains.match.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchExternalExerciseAdapter implements ExerciseExternalOutPort {

    private final MatchExternalQuery matchExternalQuery;

    @Override
    public ExerciseStatus getExerciseStatus(ExerciseId exerciseId) {
        String statusString = matchExternalQuery.findExerciseStatus(exerciseId.id());
        return ExerciseStatus.valueOf(statusString);
    }

    @Override
    public List<ExerciseUser> getParticipatedExerciseUsers(ExerciseId exerciseId) {
        final var entities = matchExternalQuery.findParticipatedExerciseUsers(exerciseId.id());
        return entities.stream()
            .map(this::toExerciseUser)
            .collect(Collectors.toList());
    }

    @Override
    public ExerciseUser getExerciseUser(ExerciseId exerciseId, UserId creatorId) {
        final var entity = matchExternalQuery.findExerciseUser(exerciseId.id(), creatorId.id());
        return toExerciseUser(entity);
    }

    @Override
    public void updateExerciseUser(Match match, boolean byDelete) {
        List<Long> userIds = match.getUserIds().stream()
            .map(UserId::id)
            .toList();
        if (byDelete) {
            matchExternalQuery.updateExerciseUserMatchStatus(match.getMatchId().id(), userIds);
        } else {
            matchExternalQuery.updateExerciseUserMatchStatus(match.getMatchId().id(), userIds, match.getMatchStatus());
        }
    }

    @Override
    public void updateExerciseUser(MatchId matchId, Match match) {
        List<Long> userIds = match.getUserIds().stream()
            .map(UserId::id)
            .toList();
        matchExternalQuery.updateExerciseUserMatchStatus(matchId.id(), userIds, match.getMatchStatus());
    }

    private ExerciseUser toExerciseUser(ExerciseUserEntity entity) {
        return ExerciseUser.builder()
            .userId(new UserId(entity.getUserId()))
            .role(ExerciseUserRole.valueOf(entity.getRole().name()))
            .status(ExerciseUserStatus.valueOf(entity.getStatus().name()))
            .build();
    }
}
