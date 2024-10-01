package hotil.baemo.domains.exercise.domain.service;

import hotil.baemo.domains.exercise.domain.value.club.ClubRole;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

public interface ClubService {

    ClubRole getClubRole(ClubId clubId, UserId userId);

    ClubRole getClubRole(ExerciseId exerciseId, UserId userId);
}
