package hotil.baemo.domains.exercise.application.ports.input.exercise.command;

import hotil.baemo.domains.exercise.application.ports.output.CommandExerciseOutputPort;
import hotil.baemo.domains.exercise.application.ports.output.ExerciseEventOutPort;
import hotil.baemo.domains.exercise.application.usecases.exercise.command.CreateExerciseUseCase;
import hotil.baemo.domains.exercise.domain.aggregate.ClubExercise;
import hotil.baemo.domains.exercise.domain.aggregate.Exercise;
import hotil.baemo.domains.exercise.domain.roles.ExerciseRule;
import hotil.baemo.domains.exercise.domain.service.ClubService;
import hotil.baemo.domains.exercise.domain.specification.exercise.command.CreateExerciseSpecification;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.*;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserStatus;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CreateExerciseInPort implements CreateExerciseUseCase {

    private final CommandExerciseOutputPort commandExercisePort;
    private final ExerciseEventOutPort exerciseEventOutPort;
    private final ClubService clubService;

    @Override
    public void createExercise(
        UserId userId,
        Title title,
        Description description,
        ParticipantNumber participantLimit,
        Location location,
        ExerciseTime exerciseTime,
        MultipartFile thumbnail
    ) {
        Exercise exercise = CreateExerciseSpecification.spec(ExerciseRule.of(ExerciseUserStatus.PARTICIPATE, ExerciseUserRole.ADMIN))
            .createExercise(userId, title, description, participantLimit, location, exerciseTime);
        ExerciseId exerciseId = commandExercisePort.save(exercise, thumbnail);
        exerciseEventOutPort.exerciseCreated(exerciseId, exercise, userId);
    }

    @Override
    public void createClubExercise(
        UserId userId,
        ClubId clubId,
        ParticipantNumber guestLimit,
        Title title,
        Description description,
        ParticipantNumber participantLimit,
        Location location,
        ExerciseTime exerciseTime,
        MultipartFile thumbnail
    ) {
        ExerciseRule role = ExerciseRule.fromClubRole(clubService.getClubRole(clubId, userId));
        ClubExercise clubExercise = CreateExerciseSpecification.spec(role)
            .createClubExercise(userId, clubId, guestLimit, title, description, participantLimit, location, exerciseTime);
        ExerciseId exerciseId = commandExercisePort.save(clubExercise, thumbnail);
        exerciseEventOutPort.exerciseCreated(exerciseId, clubExercise, userId);
    }
}
