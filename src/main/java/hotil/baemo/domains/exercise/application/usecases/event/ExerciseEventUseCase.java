package hotil.baemo.domains.exercise.application.usecases.event;

import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;

public interface ExerciseEventUseCase {

    void userDeleted(UserId userId);

    void clubDeleted(ClubId clubId);

    void clubUserCanceled(ClubId clubId, UserId userId);
}
