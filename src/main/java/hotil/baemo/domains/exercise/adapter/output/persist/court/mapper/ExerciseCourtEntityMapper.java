package hotil.baemo.domains.exercise.adapter.output.persist.court.mapper;

import hotil.baemo.domains.exercise.adapter.output.persist.court.entity.ExerciseCourtEntity;
import hotil.baemo.domains.exercise.domain.entity.court.ExerciseCourt;
import hotil.baemo.domains.exercise.domain.value.exercise.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExerciseCourtEntityMapper {
    public static ExerciseCourtEntity toEntity(ExerciseCourt court) {
        return ExerciseCourtEntity.builder()
            .id(court.getId()!=null ? court.getId().id() : null)
            .exerciseId(court.getExerciseId().id())
            .courtNumber(court.getNumber().number())
            .build();
    }
    public static ExerciseCourt toDomain(ExerciseCourtEntity entity) {
        return ExerciseCourt.builder()
            .id(new ExerciseCourtId(entity.getId()))
            .exerciseId(new ExerciseId(entity.getExerciseId()))
            .number(new CourtNumber(entity.getCourtNumber()))
            .build();
    }
}