package hotil.baemo.domains.exercise.application.usecases.match.command;

import hotil.baemo.domains.exercise.domain.entity.user.MatchUsers;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

public interface CreateMatchUseCase {

    void createMatch(UserId userId, ExerciseId exerciseId, MatchUsers userList);
}
