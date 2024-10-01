package hotil.baemo.domains.notification.application.port.output;

import hotil.baemo.domains.notification.domains.value.club.ClubId;
import hotil.baemo.domains.notification.domains.value.club.ClubTitle;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseId;

public interface QueryClubOutPort {
    ClubTitle getClubTitle(ExerciseId exerciseId);

    ClubTitle getClubTitle(ClubId clubId);
}
