package hotil.baemo.domains.exercise.adapter.output.persist.exercise.command;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.ClubExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.ExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.mapper.ClubExerciseEntityMapper;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.mapper.ExerciseEntityMapper;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.repository.ClubExerciseRepository;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.repository.ExerciseRepository;
import hotil.baemo.domains.exercise.adapter.output.persist.user.repository.ExerciseUserRepository;
import hotil.baemo.domains.exercise.application.ports.output.exercise.LoadExerciseOutputPort;
import hotil.baemo.domains.exercise.domain.entity.exercise.ClubExercise;
import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoadExercisePersistAdapter implements LoadExerciseOutputPort {

    private final ExerciseRepository exerciseRepository;
    private final ClubExerciseRepository clubExerciseRepository;
    private final ExerciseUserRepository exerciseUserRepository;

    @Override
    public List<Exercise> loadAllActiveClubExercises(ClubId clubId) {
        List<ExerciseEntity> entities = exerciseRepository.findAllActiveClubExercises(clubId);
        return ExerciseEntityMapper.toDomain(entities);
    }

    @Override
    public ClubExercise loadClubExercise(ExerciseId exerciseId) {
        final var clubExercise = clubExerciseRepository.findById(exerciseId.id())
            .orElseThrow(() -> new CustomException(ResponseCode.EXERCISE_NOT_FOUND));
        final var exerciseUsers = exerciseUserRepository.findAllByExerciseId(exerciseId.id());

        return ClubExerciseEntityMapper.toDomain(clubExercise, exerciseUsers);
    }

    @Override
    public Exercise loadExercise(ExerciseId exerciseId) {
        final var exercise = exerciseRepository.findById(exerciseId.id())
            .orElseThrow(() -> new CustomException(ResponseCode.EXERCISE_NOT_FOUND));
        final var exerciseUsers = exerciseUserRepository.findAllByExerciseId(exerciseId.id());
        if (exercise instanceof ClubExerciseEntity clubExercise) {
            return ClubExerciseEntityMapper.toDomain(clubExercise, exerciseUsers);
        }
        return ExerciseEntityMapper.toDomain(exercise, exerciseUsers);
    }

    @Override
    public List<Exercise> loadAllActiveExercises(UserId userId) {
        final var entities = exerciseRepository.findAllActiveExercisesByUserId(userId);
        return entities.stream().map(exercise -> {
                var exerciseUsers = exerciseUserRepository.findAllByExerciseId(exercise.getId());
                return ExerciseEntityMapper.toDomain(exercise, exerciseUsers);
            })
            .toList();
    }

    @Override
    public List<Exercise> loadAllActiveClubExercisesByUserId(ClubId clubId, UserId userId) {
        List<ExerciseEntity> entities = exerciseRepository.findAllActiveClubExercisesByUserId(clubId, userId);
        return entities.stream().map(exercise -> {
                var exerciseUsers = exerciseUserRepository.findAllByExerciseId(exercise.getId());
                return ExerciseEntityMapper.toDomain(exercise, exerciseUsers);
            })
            .toList();

    }

    @Override
    public List<Exercise> loadExerciseByEndTime(ZonedDateTime time) {
        List<ExerciseEntity> exercises = exerciseRepository.findAllByEndTime(time);
        return ExerciseEntityMapper.toDomain(exercises);
    }
}
