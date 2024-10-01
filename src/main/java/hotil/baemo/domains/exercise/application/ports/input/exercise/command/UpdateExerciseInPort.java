package hotil.baemo.domains.exercise.application.ports.input.exercise.command;

import hotil.baemo.domains.exercise.application.ports.output.CommandExerciseOutputPort;
import hotil.baemo.domains.exercise.application.usecases.exercise.command.UpdateExerciseUseCase;
import hotil.baemo.domains.exercise.domain.aggregate.ClubExercise;
import hotil.baemo.domains.exercise.domain.aggregate.Exercise;
import hotil.baemo.domains.exercise.domain.roles.ExerciseRule;
import hotil.baemo.domains.exercise.domain.roles.RuleSpecification;
import hotil.baemo.domains.exercise.domain.specification.exercise.command.UpdateExerciseSpecification;
import hotil.baemo.domains.exercise.domain.value.exercise.*;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateExerciseInPort implements UpdateExerciseUseCase {

    private final CommandExerciseOutputPort commandExerciseOutputPort;
    private final RuleSpecification ruleSpecification;

    @Override
    public void updateExercise(
        UserId userId,
        ExerciseId exerciseId,
        Title title,
        Description description,
        ParticipantNumber participantNumber,
        Location location,
        ExerciseTime exerciseTime
    ) {
        Exercise exercise = commandExerciseOutputPort.getExercise(exerciseId);
        ExerciseRule role = ruleSpecification.getRule(ExerciseType.IMPROMPTU, exerciseId, userId);

        UpdateExerciseSpecification.spec(role, exercise)
            .updateExercise(exercise, title, description, participantNumber, location, exerciseTime);
        commandExerciseOutputPort.updateExercise(exercise);
    }


    @Override
    public void updateClubExercise(
        UserId userId,
        ExerciseId exerciseId,
        Title title,
        ParticipantNumber guestLimit,
        Description description,
        ParticipantNumber participantNumber,
        Location location,
        ExerciseTime exerciseTime
    ) {
        ClubExercise clubExercise = commandExerciseOutputPort.getClubExercise(exerciseId);
        ExerciseRule role = ruleSpecification.getRule(ExerciseType.CLUB, exerciseId, userId);

        UpdateExerciseSpecification.spec(role, clubExercise)
            .updateClubExercise(clubExercise, title, guestLimit, description, participantNumber, location, exerciseTime);
        commandExerciseOutputPort.updateExercise(clubExercise);
    }

    @Override
    public void updateThumbnail(UserId userId, ExerciseId exerciseId, MultipartFile thumbnail) {
        Exercise exercise = commandExerciseOutputPort.getExercise(exerciseId);
        ExerciseRule role = ruleSpecification.getRule(ExerciseType.IMPROMPTU, exerciseId, userId);
        UpdateExerciseSpecification.spec(role, exercise);
        commandExerciseOutputPort.updateThumbnail(exercise, thumbnail);
    }
}
