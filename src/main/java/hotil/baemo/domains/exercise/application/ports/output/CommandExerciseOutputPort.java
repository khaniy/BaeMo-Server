package hotil.baemo.domains.exercise.application.ports.output;

import hotil.baemo.domains.exercise.domain.aggregate.ClubExercise;
import hotil.baemo.domains.exercise.domain.aggregate.Exercise;
import hotil.baemo.domains.exercise.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.exercise.domain.service.ExerciseService;
import hotil.baemo.domains.exercise.domain.service.ExerciseUserService;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;
import java.util.List;

public interface CommandExerciseOutputPort extends ExerciseService, ExerciseUserService {

    ExerciseId save(Exercise exercise);

    ExerciseId save(Exercise exercise, MultipartFile thumbnail);

    void saveExerciseUser(ExerciseId exerciseId, ExerciseUser user);

    ClubExercise getClubExercise(ExerciseId exerciseId);

    Exercise getExercise(ExerciseId exerciseId);

    void updateExercise(Exercise exercise);

    void updateThumbnail(Exercise exercise, MultipartFile thumbnail);

    List<Exercise> getExercisesByEndTime(ZonedDateTime time);

    List<Exercise> getExercisesByStartTime(ZonedDateTime time);

    void deleteExercise(Exercise exercise);

    void deleteExerciseUser(ExerciseId exerciseId, ExerciseUser user);

    List<Exercise> deleteExerciseUsersByUserId(UserId userId);

    void updateExercises(List<Exercise> exercises);

    List<ClubExercise> deleteClubExerciseUsersByUserId(ClubId clubId, UserId userId);

    List<ClubExercise> deleteClubExercises(ClubId clubId);
}
