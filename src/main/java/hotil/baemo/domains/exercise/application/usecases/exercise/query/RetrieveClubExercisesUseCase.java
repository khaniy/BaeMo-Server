package hotil.baemo.domains.exercise.application.usecases.exercise.query;

import hotil.baemo.domains.exercise.application.dto.QExerciseDTO;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RetrieveClubExercisesUseCase {

    List<QExerciseDTO.ExerciseListView> retrieveClubExercises(ClubId clubId, UserId userId, Pageable pageable);

    List<QExerciseDTO.ExerciseListView> retrieveClubHomeExercises(ClubId clubId, UserId userId);
}
