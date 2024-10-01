package hotil.baemo.domains.exercise.application.usecases.exercise.command;

import hotil.baemo.domains.exercise.domain.value.exercise.*;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import org.springframework.web.multipart.MultipartFile;

public interface UpdateExerciseUseCase {

    void updateExercise(UserId userId, ExerciseId exerciseId, Title title, Description description, ParticipantNumber participantNumber, Location location, ExerciseTime exerciseTime);

    void updateClubExercise(UserId userId, ExerciseId exerciseId, Title title, ParticipantNumber guestLimit, Description description, ParticipantNumber participantNumber, Location location, ExerciseTime exerciseTime);

    void updateThumbnail(UserId userId, ExerciseId exerciseId, MultipartFile thumbnail);
}
