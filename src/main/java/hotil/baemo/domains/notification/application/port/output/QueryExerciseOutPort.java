package hotil.baemo.domains.notification.application.port.output;

import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomName;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import hotil.baemo.domains.notification.domains.value.club.ClubTitle;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseId;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseTitle;

public interface QueryExerciseOutPort {
	ExerciseTitle getExerciseTitle(ExerciseId exerciseId);
}
