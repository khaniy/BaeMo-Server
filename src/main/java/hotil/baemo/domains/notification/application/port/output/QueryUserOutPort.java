package hotil.baemo.domains.notification.application.port.output;

import hotil.baemo.domains.notification.domains.value.club.ClubTitle;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseId;
import hotil.baemo.domains.notification.domains.value.user.UserId;
import hotil.baemo.domains.notification.domains.value.user.UserName;

public interface QueryUserOutPort {

    UserName getUserName(UserId participantUserId);
}
