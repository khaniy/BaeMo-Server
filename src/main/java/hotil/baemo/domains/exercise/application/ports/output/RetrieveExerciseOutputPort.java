package hotil.baemo.domains.exercise.application.ports.output;

import hotil.baemo.domains.exercise.application.dto.QExerciseDTO;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RetrieveExerciseOutputPort {

    List<QExerciseDTO.ExerciseListView> getUserCompleteExercises(UserId userId);

    List<QExerciseDTO.ExerciseListView> getClubExercises(ClubId clubId, Pageable pageable);

    QExerciseDTO.ExerciseDetailView getExerciseDetail(ExerciseId exerciseId);

    List<QExerciseDTO.ExerciseListView> getMainPageExercises();

    QExerciseDTO.MyExercise getMyActivePageExercises(UserId userId);

    List<QExerciseDTO.ExerciseListView> getUserExercises(UserId userId);

    List<QExerciseDTO.ExerciseListView> getAllExercises(Pageable pageable);

    List<QExerciseDTO.ExerciseListView> getClubHomeExercises(ClubId clubId);
}
