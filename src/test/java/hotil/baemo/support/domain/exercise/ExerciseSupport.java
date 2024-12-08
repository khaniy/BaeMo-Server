package hotil.baemo.support.domain.exercise;

import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.ClubExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.entity.ExerciseEntity;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.mapper.ClubExerciseEntityMapper;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.mapper.ExerciseEntityMapper;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.repository.ClubExerciseRepository;
import hotil.baemo.domains.exercise.adapter.output.persist.exercise.command.repository.ExerciseRepository;
import hotil.baemo.domains.exercise.application.ports.output.exercise.ExerciseEventOutPort;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseStatus;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseType;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserRole;
import hotil.baemo.domains.exercise.domain.value.user.ExerciseUserStatus;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Transactional
@Service
public class ExerciseSupport extends FixtureMonkeyBaseSupport {

    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private ExerciseUserSupport exerciseUserSupport;
    @Autowired
    private ExerciseEventOutPort exerciseEventOutPort;
    @Autowired
    private ClubExerciseRepository clubExerciseRepository;
    @Autowired
    private ExerciseLocationSupport exerciseLocationSupport;

    public Long setUpExercise(Long userId) {

        var exerciseEntity = exerciseRepository.save(sampleExerciseEntity(ExerciseStatus.RECRUITING));
        var exerciseId = exerciseEntity.getId();
        exerciseUserSupport.setUpMember(exerciseId, userId, ExerciseUserRole.ADMIN, ExerciseUserStatus.PARTICIPATE);
        exerciseLocationSupport.setLocation(exerciseId);
        exerciseEventOutPort.exerciseCreated(ExerciseEntityMapper.toDomain(exerciseEntity), new UserId(userId));
        return exerciseId;
    }

    public Long setUpExercise(Long userId, ExerciseStatus status) {

        var exerciseEntity = exerciseRepository.save(sampleExerciseEntity(status));
        var exerciseId = exerciseEntity.getId();
        exerciseUserSupport.setUpMember(exerciseId, userId, ExerciseUserRole.ADMIN, ExerciseUserStatus.PARTICIPATE);
        exerciseLocationSupport.setLocation(exerciseId);
        exerciseEventOutPort.exerciseCreated(ExerciseEntityMapper.toDomain(exerciseEntity), new UserId(userId));
        return exerciseId;
    }

    public Long setClubExercise(Long userId, Long clubId) {

        var exerciseEntity = clubExerciseRepository.save(sampleClubExerciseEntity(clubId, ExerciseStatus.RECRUITING));
        var exerciseId = exerciseEntity.getId();
        exerciseUserSupport.setUpMember(exerciseId, userId, ExerciseUserRole.ADMIN, ExerciseUserStatus.PARTICIPATE);
        exerciseLocationSupport.setLocation(exerciseId);
        exerciseEventOutPort.exerciseCreated(ClubExerciseEntityMapper.toDomain(exerciseEntity), new UserId(userId));
        return exerciseId;
    }

    private ExerciseEntity sampleExerciseEntity(ExerciseStatus status) {
        return monkey.giveMeBuilder(ExerciseSample.class)
            .set("exerciseStartTime", ZonedDateTime.now().plusHours(1))
            .set("exerciseEndTime", ZonedDateTime.now().plusHours(3))
            .sample()
            .toEntity(status);
    }

    private ClubExerciseEntity sampleClubExerciseEntity(Long clubId, ExerciseStatus status) {
        return monkey.giveMeBuilder(ClubExerciseSample.class)
            .set("exerciseStartTime", ZonedDateTime.now().plusHours(1))
            .set("exerciseEndTime", ZonedDateTime.now().plusHours(3))
            .sample()
            .toEntity(clubId, status);
    }

    private record ExerciseSample(
        @NotBlank @Length(max = 20) String title,
        @NotNull @Length(max = 500) String description,
        @Min(value = 8) @Max(value = 100) @NotNull Integer participantLimit,
        @Min(value = 4) @Max(value = 100) @NotNull Integer currentParticipant,
        @NotBlank String location,
        @NotBlank String thumbnailUrl,
        @NotNull @FutureOrPresent ZonedDateTime exerciseStartTime,
        @NotNull @FutureOrPresent ZonedDateTime exerciseEndTime
    ) {
        private ExerciseEntity toEntity(ExerciseStatus status) {
            return ExerciseEntity.builder()
                .exerciseType(ExerciseType.IMPROMPTU)
                .title(title)
                .description(description)
                .exerciseStatus(status)
                .exerciseStartTime(exerciseStartTime)
                .exerciseEndTime(exerciseEndTime)
                .participantLimit(participantLimit)
                .currentParticipant(participantLimit - 3)
                .location(location)
                .thumbnailUrl(thumbnailUrl)
                .isDel(false)
                .build();
        }
    }

    private record ClubExerciseSample(
        @NotBlank @Length(max = 20) String title,
        @NotNull @Length(max = 500) String description,
        @Min(value = 4) @Max(value = 100) @NotNull Integer participantLimit,
        @Min(value = 1) @Max(value = 100) @NotNull Integer guestLimit,
        @NotBlank String location,
        @NotBlank String thumbnailUrl,
        @NotNull @FutureOrPresent ZonedDateTime exerciseStartTime,
        @NotNull @FutureOrPresent ZonedDateTime exerciseEndTime
    ) {
        private ClubExerciseEntity toEntity(Long clubId, ExerciseStatus status) {
            return ClubExerciseEntity.builder()
                .clubId(clubId)
                .exerciseType(ExerciseType.CLUB)
                .title(title)
                .description(description)
                .exerciseStatus(status)
                .exerciseStartTime(exerciseStartTime)
                .exerciseEndTime(exerciseEndTime)
                .participantLimit(participantLimit)
                .currentParticipant(participantLimit - 3)
                .guestLimit(guestLimit)
                .currentParticipantGuest(guestLimit - 1)
                .location(location)
                .thumbnailUrl(thumbnailUrl)
                .isDel(false)
                .build();

        }
    }
}