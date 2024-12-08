package hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.mapper;

import hotil.baemo.domains.exercise.adapter.output.persist.user.entity.ExerciseUserEntity;
import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExerciseUserEntityMapper {


    public static List<ExerciseUserEntity> toEntities(ExerciseId exerciseId, Exercise exercise) {
        if (exercise.getExerciseUsers() != null) {
            return exercise.getExerciseUsers().getUsers().stream().map(
                user -> ExerciseUserEntityMapper.toEntity(exerciseId, user)
            ).toList();
        }
        return null;
    }

    public static List<ExerciseUserEntity> toEntities(Exercise exercise) {
        return exercise.getExerciseUsers().getUsers().stream()
            .map(user -> ExerciseUserEntityMapper.toEntity(exercise.getExerciseId(), user))
            .toList();
    }

    public static List<ExerciseUserEntity> toEntities(List<Exercise> exercises) {
        return exercises.stream()
            .flatMap(exercise -> ExerciseUserEntityMapper.toEntities(exercise).stream())
            .toList();
    }

    public static ExerciseUserEntity toEntity(ExerciseId exerciseId, ExerciseUser exerciseUser) {
        return ExerciseUserEntity.builder()
            .id(exerciseUser.getId() != null ? exerciseUser.getId().id() : null)
            .userId(exerciseUser.getUserId().id())
            .exerciseId(exerciseId.id())
            .role(exerciseUser.getRole())
            .status(exerciseUser.getStatus())
            .matchStatus(exerciseUser.getMatchStatus())
            .appliedBy(exerciseUser.getAppliedBy().id())
            .isDel(false)
            .build();
    }

    public static ExerciseUserEntity toEntity(ExerciseUser exerciseUser) {
        return ExerciseUserEntity.builder()
            .id(exerciseUser.getId() != null ? exerciseUser.getId().id() : null)
            .userId(exerciseUser.getUserId().id())
            .exerciseId(exerciseUser.getExerciseId().id())
            .role(exerciseUser.getRole())
            .status(exerciseUser.getStatus())
            .matchStatus(exerciseUser.getMatchStatus())
            .appliedBy(exerciseUser.getAppliedBy().id())
            .isDel(false)
            .build();
    }

    public static List<ExerciseUser> toDomain(List<ExerciseUserEntity> entities) {
        return entities.stream().map(ExerciseUserEntityMapper::toDomain).collect(Collectors.toList());
    }


    public static ExerciseUser toDomain(ExerciseUserEntity entity) {
        return ExerciseUser.builder()
            .id(new ExerciseUserId(entity.getId()))
            .exerciseId(new ExerciseId(entity.getExerciseId()))
            .userId(new UserId(entity.getUserId()))
            .role(entity.getRole())
            .status(entity.getStatus())
            .matchStatus(entity.getMatchStatus())
            .appliedBy(new UserId(entity.getAppliedBy()))
            .updatedAt(entity.getUpdatedAt())
            .build();
    }

}
