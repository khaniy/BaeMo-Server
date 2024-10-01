package hotil.baemo.domains.match.application.usecase.command;

import hotil.baemo.domains.match.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.match.domain.aggregate.MatchUsers;
import hotil.baemo.domains.match.domain.value.user.UserId;
import hotil.baemo.domains.match.domain.value.match.CourtNumber;

public interface CreateMatchUseCase {

    void createMatch(UserId creatorId, ExerciseId exerciseId, MatchUsers userList, CourtNumber courtNumber);
}
