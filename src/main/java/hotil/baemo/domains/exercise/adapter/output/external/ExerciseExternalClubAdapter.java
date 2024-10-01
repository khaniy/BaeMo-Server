package hotil.baemo.domains.exercise.adapter.output.external;

import hotil.baemo.domains.exercise.adapter.output.external.query.ExerciseExternalQuery;
import hotil.baemo.domains.exercise.application.ports.output.ExerciseExternalClubOutputPort;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.club.ClubRole;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExerciseExternalClubAdapter implements ExerciseExternalClubOutputPort {

    private final ExerciseExternalQuery externalQuery;

    @Override
    public ClubRole getClubRole(ClubId clubId, UserId userId) {
        String clubUserRole = externalQuery.getClubUserRole(clubId.clubId(), userId.id());
        return ClubRole.valueOf(clubUserRole);
    }

    @Override
    public ClubRole getClubRole(ExerciseId exerciseId, UserId userId) {
        String clubUserRole = externalQuery.getClubUserRoleByExerciseId(exerciseId.id(), userId.id());
        return ClubRole.valueOf(clubUserRole);
    }

    @Override
    public List<ClubId> getClubIds(UserId userId) {
        return externalQuery.getClubIds(userId.id());
    }

    @Override
    public List<UserId> getClubUserIds(ClubId clubId) {
        List<Long> userIds = externalQuery.getClubUserIds(clubId.clubId());
        return userIds.stream().map(UserId::new).collect(Collectors.toList());
    }
}
