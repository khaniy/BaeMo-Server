package hotil.baemo.domains.exercise.adapter.output.persist;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.adapter.output.persist.entity.ClubExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.entity.ExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.entity.ExerciseUserEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.mapper.ExerciseEntityMapper;
import hotil.baemo.domains.exercise.adapter.output.persist.mapper.ExerciseUserEntityMapper;
import hotil.baemo.domains.exercise.adapter.output.persist.repository.*;
import hotil.baemo.domains.exercise.adapter.output.storage.ExerciseObjectStorageAdapter;
import hotil.baemo.domains.exercise.application.ports.output.CommandExerciseOutputPort;
import hotil.baemo.domains.exercise.domain.aggregate.ClubExercise;
import hotil.baemo.domains.exercise.domain.aggregate.Exercise;
import hotil.baemo.domains.exercise.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseThumbnail;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseType;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandExercisePersistAdapter implements CommandExerciseOutputPort {
    private final ExerciseJpaRepository exerciseRepository;
    private final ClubExerciseJpaRepository clubExerciseRepository;
    private final ExerciseUserJpaRepository exerciseUserRepository;
    private final ExerciseObjectStorageAdapter exerciseObjectStorageAdapter;
    private final QueryExerciseUserQRepository queryExerciseUserQRepository;
    private final QueryExerciseQRepository queryExerciseQRepository;


    @Override
    public ExerciseId save(Exercise exercise) {
        ExerciseEntity exerciseEntity;
        if (exercise instanceof ClubExercise) {
            exerciseEntity = clubExerciseRepository.save(ExerciseEntityMapper.toEntity((ClubExercise) exercise));
        } else {
            exerciseEntity = exerciseRepository.save(ExerciseEntityMapper.toEntity(exercise));
        }

        exerciseUserRepository.saveAll(ExerciseUserEntityMapper.toEntities(
            new ExerciseId(exerciseEntity.getId()),
            exercise.getExerciseUsers().getUsers()));

        return new ExerciseId(exerciseEntity.getId());
    }

    @Override
    public ExerciseId save(Exercise exercise, MultipartFile thumbnail) {
        String url = exerciseObjectStorageAdapter.saveThumbnail(thumbnail);
        exercise.updateThumbnail(new ExerciseThumbnail(url));
        return save(exercise);
    }

    @Override
    public void saveExerciseUser(ExerciseId exerciseId, ExerciseUser user) {
        ExerciseUserEntity entity = ExerciseUserEntityMapper.toEntity(exerciseId, user);
        exerciseUserRepository.save(entity);
    }

    @Override
    public ExerciseUser getUserForCheckRule(ExerciseId exerciseId, UserId userId) {
        Optional<ExerciseUserEntity> entity = exerciseUserRepository.findByUserIdAndExerciseId(userId.id(), exerciseId.id());
        return entity.map(ExerciseUserEntityMapper::toDomain).orElse(null);
    }

    @Override
    public ClubExercise getClubExercise(ExerciseId exerciseId) {
        ClubExerciseEntity exerciseEntity = clubExerciseRepository.findById(exerciseId.id())
            .orElseThrow(() -> new CustomException(ResponseCode.EXERCISE_NOT_FOUND));
        List<ExerciseUserEntity> exerciseUserEntityList = exerciseUserRepository.findByExerciseId(exerciseId.id());

        return ExerciseEntityMapper.toDomain(exerciseEntity, exerciseUserEntityList);
    }

    @Override
    public ExerciseType getExerciseType(ExerciseId exerciseId) {
        ExerciseEntity exerciseEntity = exerciseRepository.findById(exerciseId.id())
            .orElseThrow(() -> new CustomException(ResponseCode.EXERCISE_NOT_FOUND));
        return exerciseEntity.getExerciseType();
    }

    @Override
    public Exercise getExercise(ExerciseId exerciseId) {
        ExerciseEntity exerciseEntity = exerciseRepository.findById(exerciseId.id())
            .orElseThrow(() -> new CustomException(ResponseCode.EXERCISE_NOT_FOUND));
        List<ExerciseUserEntity> exerciseUserEntityList = exerciseUserRepository.findByExerciseId(exerciseId.id());
        if (exerciseEntity instanceof ClubExerciseEntity) {
            return ExerciseEntityMapper.toDomain((ClubExerciseEntity) exerciseEntity, exerciseUserEntityList);
        }
        return ExerciseEntityMapper.toDomain(exerciseEntity, exerciseUserEntityList);
    }

    @Override
    public List<Exercise> getExercisesByStartTime(ZonedDateTime time) {
        List<ExerciseEntity> exercises = exerciseRepository.findAllByStartTime(time);
        return ExerciseEntityMapper.toDomain(exercises);
    }

    @Override
    public List<Exercise> getExercisesByEndTime(ZonedDateTime time) {
        List<ExerciseEntity> exercises = exerciseRepository.findAllByEndTime(time);
        return ExerciseEntityMapper.toDomain(exercises);
    }

    @Override
    public void updateExercise(Exercise exercise) {
        if (exercise instanceof ClubExercise) {
            clubExerciseRepository.save(ExerciseEntityMapper.toEntity((ClubExercise) exercise));
        } else {
            exerciseRepository.save(ExerciseEntityMapper.toEntity(exercise));
        }
    }

    @Override
    public void updateExercises(List<Exercise> exercises) {
        final var exerciseEntities = exercises.stream()
            .filter(exercise -> !(exercise instanceof ClubExercise))
            .map(ExerciseEntityMapper::toEntity)
            .collect(Collectors.toList());

        final var clubExerciseEntities = exercises.stream()
            .filter(exercise -> exercise instanceof ClubExercise)
            .map(exercise -> ExerciseEntityMapper.toEntity((ClubExercise) exercise))
            .collect(Collectors.toList());
        if (!exerciseEntities.isEmpty()) {
            exerciseRepository.saveAll(exerciseEntities);
        }
        if (!clubExerciseEntities.isEmpty()) {
            clubExerciseRepository.saveAll(clubExerciseEntities);
        }
    }

    @Override
    public void updateThumbnail(Exercise exercise, MultipartFile thumbnail) {
        String url = exerciseObjectStorageAdapter.saveThumbnail(thumbnail);
        exercise.updateThumbnail(new ExerciseThumbnail(url));
        if (exercise instanceof ClubExercise) {
            clubExerciseRepository.save(ExerciseEntityMapper.toEntity((ClubExercise) exercise));
        } else {
            exerciseRepository.save(ExerciseEntityMapper.toEntity(exercise));
        }
    }

    @Override
    public void deleteExercise(Exercise exercise) {
        if (exercise instanceof ClubExercise) {
            clubExerciseRepository.save(ExerciseEntityMapper.toEntity((ClubExercise) exercise));
        } else {
            exerciseRepository.save(ExerciseEntityMapper.toEntity(exercise));
        }
        exerciseUserRepository.deleteAll(ExerciseUserEntityMapper.toEntities(
            exercise.getExerciseId(),
            exercise.getExerciseUsers().getUsers()));
    }

    @Override
    public void deleteExerciseUser(ExerciseId exerciseId, ExerciseUser user) {
        exerciseUserRepository.delete(ExerciseUserEntityMapper.toEntity(exerciseId, user));
    }

    @Override
    public List<Exercise> deleteExerciseUsersByUserId(UserId userId) {
        List<ExerciseEntity> entities = queryExerciseUserQRepository.findExerciseByUserId(userId.id());
        exerciseUserRepository.deleteAllByUserId(userId.id());
        return entities.stream().map(ExerciseEntityMapper::toDomain).toList();
    }

    @Override
    public List<ClubExercise> deleteClubExerciseUsersByUserId(ClubId clubId, UserId userId) {
        List<ClubExerciseEntity> entities = queryExerciseQRepository.findProgressClubExercises(clubId.clubId());
        exerciseUserRepository.deleteAllByExerciseIdsAndUserId(
            entities.stream().map(ClubExerciseEntity::getId).toList(),
            userId.id()
        );
        return entities.stream().map(ExerciseEntityMapper::toDomain).toList();
    }

    @Override
    public List<ClubExercise> deleteClubExercises(ClubId clubId) {
        List<ClubExerciseEntity> entities = queryExerciseQRepository.findProgressClubExercises(clubId.clubId());
        clubExerciseRepository.deleteAll(entities);
        return entities.stream().map(ExerciseEntityMapper::toDomain).toList();
    }
}
