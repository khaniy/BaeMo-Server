package hotil.baemo.domains.exercise.application.ports.output.user;

import hotil.baemo.domains.exercise.domain.entity.match.Match;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.match.MatchId;

import java.util.List;

public interface CommandExerciseUserOutPort {

    void saveExerciseUser(ExerciseId exerciseId, ExerciseUser user);

    void updateExerciseUser(Match match);

    void deleteAllUsers(List<ExerciseUser> exerciseUsers);

    void deleteUser(ExerciseUser user);
}
