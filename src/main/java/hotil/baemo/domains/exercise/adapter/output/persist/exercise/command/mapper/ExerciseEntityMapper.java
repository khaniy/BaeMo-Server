package hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.mapper;

import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.ClubExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.ExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.user.entity.ExerciseUserEntity;
import hotil.baemo.domains.exercise.domain.entity.exercise.Exercise;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUsers;
import hotil.baemo.domains.exercise.domain.value.exercise.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExerciseEntityMapper {

    public static ExerciseEntity toEntity(Exercise exercise) {
        return buildExerciseEntity(exercise)
            .build();
    }

    public static Exercise toDomain(ExerciseEntity exerciseEntity) {
        if (exerciseEntity instanceof ClubExerciseEntity clubExerciseEntity) {
            return ClubExerciseEntityMapper.toDomain(clubExerciseEntity);
        }
        return buildExercise(exerciseEntity)
            .build();
    }

    public static Exercise toDomain(ExerciseEntity exerciseEntity, List<ExerciseUserEntity> exerciseUserEntities) {
        return buildExercise(exerciseEntity)
            .exerciseUsers(ExerciseUsers.of(ExerciseUserEntityMapper.toDomain(exerciseUserEntities)))
            .build();
    }

    public static List<Exercise> toDomain(List<ExerciseEntity> exercises) {
        return exercises.stream().map(exercise -> {
                if (exercise instanceof ClubExerciseEntity clubExerciseEntity) {
                    return ClubExerciseEntityMapper.toDomain(clubExerciseEntity);
                }
                return toDomain(exercise);
            })
            .collect(Collectors.toList());
    }


    private static ExerciseEntity.ExerciseEntityBuilder<?, ?> buildExerciseEntity(Exercise e) {
        return ExerciseEntity.builder()
            .id(e.getExerciseId() != null ? e.getExerciseId().id() : null)
            .exerciseType(e.getExerciseType())
            .title(e.getTitle().title())
            .description(e.getDescription().description())
            .exerciseStatus(e.getExerciseStatus())
            .exerciseStartTime(e.getExerciseTime().startTime())
            .exerciseEndTime(e.getExerciseTime().endTime())
            .participantLimit(e.getParticipantLimit().number())
            .currentParticipant(e.getCurrentParticipant().number())
            .location(e.getLocation().location())
            .thumbnailUrl(e.getThumbnailUrl() != null ? e.getThumbnailUrl().url() : null)
            .isDel(e.isDel());
    }

    private static Exercise.ExerciseBuilder<?, ?> buildExercise(ExerciseEntity e) {
        return Exercise.builder()
            .exerciseId(new ExerciseId(e.getId()))
            .exerciseType(e.getExerciseType())
            .title(new Title(e.getTitle()))
            .description(new Description(e.getDescription()))
            .participantLimit(new ParticipantNumber(e.getParticipantLimit()))
            .currentParticipant(new ParticipantNumber(e.getCurrentParticipant()))
            .exerciseType(e.getExerciseType())
            .location(new Location(e.getLocation()))
            .exerciseStatus(e.getExerciseStatus())
            .exerciseTime(new ExerciseTime(e.getExerciseStartTime(), e.getExerciseEndTime()))
            .thumbnailUrl(e.getThumbnailUrl() != null ? new ExerciseThumbnailUrl(e.getThumbnailUrl()) : null)
            .isDel(e.isDel());
    }
}