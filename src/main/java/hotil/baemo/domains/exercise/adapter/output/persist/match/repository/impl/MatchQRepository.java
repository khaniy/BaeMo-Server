package hotil.baemo.domains.exercise.adapter.output.persist.match.repository.impl;

import hotil.baemo.domains.exercise.domain.entity.court.ExerciseCourt;
import hotil.baemo.domains.exercise.domain.value.exercise.CourtNumber;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;

public interface MatchQRepository {
    Boolean existProgressMatch(ExerciseId exerciseId, CourtNumber courtNumber);
}
