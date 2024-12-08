package hotil.baemo.domains.notification.adapter.output.persist;

import hotil.baemo.domains.notification.adapter.output.persist.repository.ClubQRepository;
import hotil.baemo.domains.notification.adapter.output.persist.repository.ExerciseQRepository;
import hotil.baemo.domains.notification.application.port.output.QueryClubOutPort;
import hotil.baemo.domains.notification.application.port.output.QueryExerciseOutPort;
import hotil.baemo.domains.notification.domains.value.club.ClubId;
import hotil.baemo.domains.notification.domains.value.club.ClubTitle;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseId;
import hotil.baemo.domains.notification.domains.value.exercise.ExerciseTitle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryExercisePersistAdapter implements QueryExerciseOutPort {

    private final ExerciseQRepository exerciseQRepository;

    @Override
    public ExerciseTitle getExerciseTitle(ExerciseId exerciseId) {
        String exerciseTitle = exerciseQRepository.findExerciseTitle(exerciseId);
        return new ExerciseTitle(exerciseTitle);
    }

}
