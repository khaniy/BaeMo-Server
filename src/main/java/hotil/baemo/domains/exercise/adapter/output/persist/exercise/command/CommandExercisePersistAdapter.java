package hotil.baemo.domains.exercise.adapter.output.persist.exercise.command;

import hotil.baemo.domains.exercise.adapter.output.persist.court.repository.ExerciseCourtRepository;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.ExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.mapper.ClubExerciseEntityMapper;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.mapper.ExerciseEntityMapper;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.mapper.ExerciseLocationEntityMapper;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.mapper.ExerciseUserEntityMapper;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.repository.ClubExerciseRepository;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.repository.ExerciseLocationRepository;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.repository.ExerciseRepository;
import hotil.baemo.domains.exercise.adapter.output.persist.user.repository.ExerciseUserRepository;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.storage.ExerciseObjectStorageAdapter;
import hotil.baemo.domains.exercise.application.ports.output.exercise.CommandExerciseOutputPort;
import hotil.baemo.domains.exercise.domain.entity.exercise.ClubExercise;
import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseThumbnail;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseThumbnailUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandExercisePersistAdapter implements CommandExerciseOutputPort {

    private final ExerciseRepository exerciseRepository;
    private final ClubExerciseRepository clubExerciseRepository;
    private final ExerciseLocationRepository exerciseLocationRepository;
    private final ExerciseUserRepository exerciseUserRepository;
    private final ExerciseCourtRepository exerciseCourtRepository;
    private final ExerciseObjectStorageAdapter exerciseObjectStorageAdapter;


    @Override
    public Exercise save(Exercise exercise) {
        ExerciseEntity exerciseEntity = saveExerciseByType(exercise);
        exerciseUserRepository.saveAll(
            ExerciseUserEntityMapper.toEntities(new ExerciseId(exerciseEntity.getId()), exercise)
        );
        if (exercise.getLocationCode() != null) {
            exerciseLocationRepository.save(
                ExerciseLocationEntityMapper.toEntity(new ExerciseId(exerciseEntity.getId()), exercise)
            );
        }
        return ExerciseEntityMapper.toDomain(exerciseEntity);
    }

    @Override
    public Exercise save(Exercise exercise, ExerciseThumbnail thumbnail) {
        if (thumbnail != null) {
            String url = exerciseObjectStorageAdapter.saveThumbnail(thumbnail.file());
            exercise.updateThumbnail(new ExerciseThumbnailUrl(url));
        }
        return save(exercise);
    }

    @Override
    public void saveAll(List<Exercise> exercises) {
        final var exerciseEntities = exercises.stream()
            .filter(exercise -> !(exercise instanceof ClubExercise))
            .map(ExerciseEntityMapper::toEntity)
            .collect(Collectors.toList());

        final var clubExerciseEntities = exercises.stream()
            .filter(exercise -> exercise instanceof ClubExercise)
            .map(exercise -> ClubExerciseEntityMapper.toEntity((ClubExercise) exercise))
            .collect(Collectors.toList());
        if (!exerciseEntities.isEmpty()) {
            exerciseRepository.saveAll(exerciseEntities);
        }
        if (!clubExerciseEntities.isEmpty()) {
            clubExerciseRepository.saveAll(clubExerciseEntities);
        }
    }

    @Override
    public void delete(Exercise exercise) {
        saveExerciseByType(exercise);
        exerciseUserRepository.deleteAllByExerciseId(exercise.getExerciseId().id());
        exerciseLocationRepository.deleteByExerciseId(exercise.getExerciseId().id());
        exerciseCourtRepository.deleteAllByExerciseId(exercise.getExerciseId().id());
    }

    @Override
    public void deleteAll(List<Exercise> exercises) {
        exercises.forEach(this::delete);
    }

    private ExerciseEntity saveExerciseByType(Exercise exercise) {
        ExerciseEntity exerciseEntity;
        if (exercise instanceof ClubExercise clubExercise) {
            exerciseEntity = clubExerciseRepository.save(ClubExerciseEntityMapper.toEntity(clubExercise));
        } else {
            exerciseEntity = exerciseRepository.save(ExerciseEntityMapper.toEntity(exercise));
        }
        return exerciseEntity;
    }
}
