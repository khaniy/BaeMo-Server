package hotil.baemo.domains.exercise.application.ports.input.exercise.query;

import hotil.baemo.domains.exercise.application.dto.QExerciseDTO;
import hotil.baemo.domains.exercise.application.ports.output.RetrieveExerciseOutputPort;
import hotil.baemo.domains.exercise.application.usecases.exercise.query.RetrieveClubExercisesUseCase;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RetrieveClubExerciseInputPort implements RetrieveClubExercisesUseCase {

    private final RetrieveExerciseOutputPort retrieveExercisePort;

    @Override
    public List<QExerciseDTO.ExerciseListView> retrieveClubExercises(ClubId clubId, UserId userId, Pageable pageable) {

        return retrieveExercisePort.getClubExercises(clubId, pageable);
    }

    @Override
    public List<QExerciseDTO.ExerciseListView> retrieveClubHomeExercises(ClubId clubId, UserId userId) {
        return retrieveExercisePort.getClubHomeExercises(clubId);
    }
}
