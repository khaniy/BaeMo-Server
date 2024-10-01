package hotil.baemo.domains.exercise.adapter.output.persist.mapper;

import hotil.baemo.domains.exercise.adapter.output.persist.entity.ExerciseUserEntity;
import hotil.baemo.domains.exercise.domain.aggregate.ExerciseUser;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserId;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExerciseUserEntityMapper {


    public static List<ExerciseUserEntity> toEntities(ExerciseId exerciseId, List<ExerciseUser> exerciseUsers) {
        return exerciseUsers.stream().map(
            user -> ExerciseUserEntityMapper.toEntity(exerciseId, user)
        ).toList();
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

    public static List<ExerciseUser> toDomain(List<ExerciseUserEntity> entities) {
        return entities.stream().map(ExerciseUserEntityMapper::toDomain).collect(Collectors.toList());
    }


    public static ExerciseUser toDomain(ExerciseUserEntity entity) {
        return ExerciseUser.builder()
            .id(new ExerciseUserId(entity.getId()))
            .userId(new UserId(entity.getUserId()))
            .role(entity.getRole())
            .status(entity.getStatus())
            .matchStatus(entity.getMatchStatus())
            .appliedBy(new UserId(entity.getAppliedBy()))
            .updatedAt(entity.getUpdatedAt())
            .build();
    }
}
