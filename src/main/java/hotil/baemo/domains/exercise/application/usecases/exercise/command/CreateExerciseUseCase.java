package hotil.baemo.domains.exercise.application.usecases.exercise.command;

import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.*;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import org.springframework.web.multipart.MultipartFile;

public interface CreateExerciseUseCase {

    void createExercise(UserId userId, Title title, Description description, ParticipantNumber participantLimit, Location location, ExerciseTime exerciseTime, MultipartFile thumbnail);

    void createClubExercise(UserId userId, ClubId clubId, ParticipantNumber guestLimit, Title title, Description description, ParticipantNumber participantLimit, Location location, ExerciseTime exerciseTime, MultipartFile thumbnail);
}
