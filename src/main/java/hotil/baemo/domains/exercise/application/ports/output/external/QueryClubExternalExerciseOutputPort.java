package hotil.baemo.domains.exercise.application.ports.output.external;

import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

public interface QueryClubExternalExerciseOutputPort {

    boolean existClubMember(UserId userId, ClubId clubId);

    ExerciseUser getMemberFromClub(ClubId clubId, UserId userId);

    ExerciseUser getMemberFromClub(ExerciseId exerciseId, UserId userId);
}
