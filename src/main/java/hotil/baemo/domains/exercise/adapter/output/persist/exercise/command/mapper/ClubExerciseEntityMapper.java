package hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.mapper;

import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.ClubExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.user.entity.ExerciseUserEntity;
import hotil.baemo.domains.exercise.domain.entity.exercise.ClubExercise;
import hotil.baemo.domains.exercise.domain.entity.user.ExerciseUsers;
import hotil.baemo.domains.exercise.domain.value.club.ClubId;
import hotil.baemo.domains.exercise.domain.value.exercise.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClubExerciseEntityMapper {

    public static ClubExerciseEntity toEntity(ClubExercise clubExercise) {
        return buildClubExerciseEntity(clubExercise)
            .build();
    }

    public static List<ClubExerciseEntity> toEntities(List<ClubExercise> clubExercises) {
        return clubExercises.stream().map(ClubExerciseEntityMapper::toEntity).toList();
    }


    public static ClubExercise toDomain(ClubExerciseEntity exerciseEntity) {
        return buildClubExercise(exerciseEntity)
            .build();
    }

    public static ClubExercise toDomain(ClubExerciseEntity clubExerciseEntity, List<ExerciseUserEntity> exerciseUserEntities) {
        return buildClubExercise(clubExerciseEntity)
            .exerciseUsers(ExerciseUsers.of(ExerciseUserEntityMapper.toDomain(exerciseUserEntities)))
            .build();
    }

    private static ClubExerciseEntity.ClubExerciseEntityBuilder<?, ?> buildClubExerciseEntity(ClubExercise c) {
        return ClubExerciseEntity.builder()
            .id(c.getExerciseId() != null ? c.getExerciseId().id() : null)
            .exerciseType(c.getExerciseType())
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
            .thumbnailUrl(c.getThumbnailUrl() != null ? c.getThumbnailUrl().url() : null)
            .isDel(c.isDel());
    }

    private static ClubExercise.ClubExerciseBuilder<?, ?> buildClubExercise(ClubExerciseEntity c) {
        return ClubExercise.builder()
            .exerciseId(new ExerciseId(c.getId()))
            .exerciseType(c.getExerciseType())
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
            .thumbnailUrl(c.getThumbnailUrl() != null ? new ExerciseThumbnailUrl(c.getThumbnailUrl()) : null)
            .isDel(c.isDel());
    }
}