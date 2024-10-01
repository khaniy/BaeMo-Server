package hotil.baemo.domains.exercise.adapter.output.persist.mapper;

import hotil.baemo.domains.exercise.adapter.output.persist.entity.ClubExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.entity.ExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.entity.ExerciseUserEntity;
import hotil.baemo.domains.exercise.domain.aggregate.ClubExercise;
import hotil.baemo.domains.exercise.domain.aggregate.Exercise;
import hotil.baemo.domains.exercise.domain.aggregate.ExerciseUsers;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
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

    public static ClubExerciseEntity toEntity(ClubExercise clubExercise) {
        return buildClubExerciseEntity(clubExercise)
            .build();
    }

    public static Exercise toDomain(ExerciseEntity exerciseEntity) {
        return buildExercise(exerciseEntity)
            .build();
    }

    public static ClubExercise toDomain(ClubExerciseEntity exerciseEntity) {
        return buildClubExercise(exerciseEntity)
            .build();
    }

    public static Exercise toDomain(ExerciseEntity exerciseEntity, List<ExerciseUserEntity> exerciseUserEntities) {
        return buildExercise(exerciseEntity)
            .exerciseUsers(ExerciseUsers.of(ExerciseUserEntityMapper.toDomain(exerciseUserEntities)))
            .build();
    }

    public static ClubExercise toDomain(ClubExerciseEntity clubExerciseEntity, List<ExerciseUserEntity> exerciseUserEntities) {
        return buildClubExercise(clubExerciseEntity)
            .exerciseUsers(ExerciseUsers.of(ExerciseUserEntityMapper.toDomain(exerciseUserEntities)))
            .build();
    }

    public static List<Exercise> toDomain(List<ExerciseEntity> exercises) {
        return exercises.stream().map(exercise -> {
                if (exercise instanceof ClubExerciseEntity) {
                    return toDomain((ClubExerciseEntity) exercise);
                }
                return toDomain(exercise);
            })
            .collect(Collectors.toList());
    }

    public static List<Exercise> toDomain(List<ExerciseEntity> exercises, List<ExerciseUserEntity> users) {
        var collectedUsers = users.stream()
            .collect(Collectors.groupingBy(ExerciseUserEntity::getExerciseId));
        return exercises.stream()
            .map(exercise -> {
                var exerciseUsers = collectedUsers.getOrDefault(exercise.getId(), List.of());
                if (exercise instanceof ClubExerciseEntity) {
                    return toDomain((ClubExerciseEntity) exercise, exerciseUsers);
                }
                return toDomain(exercise, exerciseUsers);
            })
            .collect(Collectors.toList());
    }


    private static ExerciseEntity.ExerciseEntityBuilder<?, ?> buildExerciseEntity(Exercise e) {
        return ExerciseEntity.builder()
            .id(e.getExerciseId() != null ? e.getExerciseId().id() : null)
            .title(e.getTitle().title())
            .description(e.getDescription().description())
            .exerciseStatus(e.getExerciseStatus())
            .exerciseStartTime(e.getExerciseTime().startTime())
            .exerciseEndTime(e.getExerciseTime().endTime())
            .participantLimit(e.getParticipantLimit().number())
            .currentParticipant(e.getCurrentParticipant().number())
            .location(e.getLocation().location())
            .thumbnailUrl(e.getThumbnail().url())
            .isDel(e.isDel());
    }

    private static ClubExerciseEntity.ClubExerciseEntityBuilder<?, ?> buildClubExerciseEntity(ClubExercise c) {
        return ClubExerciseEntity.builder()
            .id(c.getExerciseId() != null ? c.getExerciseId().id() : null)
            .clubId(c.getClubId().clubId())
            .guestLimit(c.getGuestLimit().number())
            .currentParticipantGuest(c.getCurrentParticipantGuest().number())
            .title(c.getTitle().title())
            .description(c.getDescription().description())
            .exerciseStatus(c.getExerciseStatus())
            .exerciseStartTime(c.getExerciseTime().startTime())
            .exerciseEndTime(c.getExerciseTime().endTime())
            .participantLimit(c.getParticipantLimit().number())
            .currentParticipant(c.getCurrentParticipant().number())
            .location(c.getLocation().location())
            .thumbnailUrl(c.getThumbnail().url())
            .isDel(c.isDel());
    }

    private static Exercise.ExerciseBuilder<?, ?> buildExercise(ExerciseEntity e) {
        return Exercise.builder()
            .exerciseId(new ExerciseId(e.getId()))
            .title(new Title(e.getTitle()))
            .description(new Description(e.getDescription()))
            .participantLimit(new ParticipantNumber(e.getParticipantLimit()))
            .currentParticipant(new ParticipantNumber(e.getCurrentParticipant()))
            .exerciseType(e.getExerciseType())
            .location(new Location(e.getLocation()))
            .exerciseStatus(e.getExerciseStatus())
            .exerciseTime(new ExerciseTime(e.getExerciseStartTime(), e.getExerciseEndTime()))
            .thumbnail(new ExerciseThumbnail(e.getThumbnailUrl()))
            .isDel(e.isDel());
    }

    private static ClubExercise.ClubExerciseBuilder<?, ?> buildClubExercise(ClubExerciseEntity c) {
        return ClubExercise.builder()
            .exerciseId(new ExerciseId(c.getId()))
            .clubId(new ClubId(c.getClubId()))
            .guestLimit(new ParticipantNumber(c.getGuestLimit()))
            .currentParticipantGuest(new ParticipantNumber(c.getCurrentParticipantGuest()))
            .title(new Title(c.getTitle()))
            .description(new Description(c.getDescription()))
            .participantLimit(new ParticipantNumber(c.getParticipantLimit()))
            .currentParticipant(new ParticipantNumber(c.getCurrentParticipant()))
            .exerciseType(c.getExerciseType())
            .location(new Location(c.getLocation()))
            .exerciseStatus(c.getExerciseStatus())
            .exerciseTime(new ExerciseTime(c.getExerciseStartTime(), c.getExerciseEndTime()))
            .thumbnail(new ExerciseThumbnail(c.getThumbnailUrl()))
            .isDel(c.isDel());
    }
}