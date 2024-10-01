package hotil.baemo.domains.match.application.port.output;

import hotil.baemo.domains.match.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.match.domain.aggregate.Match;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.match.domain.value.match.MatchId;
import hotil.baemo.domains.match.domain.value.user.UserId;

import java.util.List;

public interface ExerciseExternalOutPort {
    ExerciseStatus getExerciseStatus(ExerciseId exerciseId);

    List<ExerciseUser> getParticipatedExerciseUsers(ExerciseId exerciseId);

    ExerciseUser getExerciseUser(ExerciseId exerciseId, UserId creatorId);

    void updateExerciseUser(Match match, boolean byDelete);

    void updateExerciseUser(MatchId matchId, Match match);
}
