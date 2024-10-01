package hotil.baemo.domains.exercise.adapter.output.persist;

import hotil.baemo.domains.exercise.adapter.output.persist.repository.QueryExerciseQRepository;
import hotil.baemo.domains.exercise.adapter.output.persist.repository.QueryExerciseUserQRepository;
import hotil.baemo.domains.exercise.application.dto.QExerciseDTO;
import hotil.baemo.domains.exercise.application.ports.output.RetrieveExerciseOutputPort;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class QueryExercisePersistAdapter implements RetrieveExerciseOutputPort {

    private final QueryExerciseUserQRepository queryExerciseUserRepository;
    private final QueryExerciseQRepository queryExerciseRepository;


    @Override
    public List<QExerciseDTO.ExerciseListView> getClubExercises(ClubId clubId, Pageable pageable) {
        return queryExerciseRepository.findClubExercises(clubId.clubId(), pageable);
    }

    @Override
    public List<QExerciseDTO.ExerciseListView> getClubHomeExercises(ClubId clubId) {
        return queryExerciseRepository.findClubHomeExercises(clubId.clubId());
    }

    @Override
    public QExerciseDTO.MyExercise getMyActivePageExercises(UserId userId) {
        List<Long> exerciseIds = queryExerciseUserRepository.findExerciseIdsByUserId(userId.id());
        List<Long> clubIds = queryExerciseRepository.findClubIdsByUserId(userId.id());

        final var myClubExercises = queryExerciseRepository.findClubsProgressExercises(clubIds);
        final var myParticipatedExercises = queryExerciseRepository.findActiveExercises(exerciseIds);

        return QExerciseDTO.MyExercise.builder()
            .myClubExercises(myClubExercises)
            .myParticipatedExercises(myParticipatedExercises)
            .build();
    }


    @Override
    public QExerciseDTO.ExerciseDetailView getExerciseDetail(ExerciseId exerciseId) {
        return queryExerciseRepository.findExercise(exerciseId.id());
    }

    @Override
    public List<QExerciseDTO.ExerciseListView> getUserCompleteExercises(UserId userId) {
        List<Long> exerciseIds = queryExerciseUserRepository.findExerciseIdsByUserId(userId.id());
        return queryExerciseRepository.findCompleteExercises(exerciseIds);
    }

    @Override
    public List<QExerciseDTO.ExerciseListView> getUserExercises(UserId userId) {
        List<Long> exerciseIds = queryExerciseUserRepository.findExerciseIdsByUserId(userId.id());
        return queryExerciseRepository.findCompleteExercises(exerciseIds);
    }

    @Override
    public List<QExerciseDTO.ExerciseListView> getMainPageExercises() {
        return queryExerciseRepository.findMainPageExercises();
    }

    @Override
    public List<QExerciseDTO.ExerciseListView> getAllExercises(Pageable pageable) {
        return queryExerciseRepository.findAllExercises(pageable);
    }
}
