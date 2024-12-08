package hotil.baemo.domains.exercise.domain.entity.court;

import hotil.baemo.core.validator.BaemoValidator;
import hotil.baemo.domains.exercise.domain.value.exercise.CourtNumber;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseCourtId;
import hotil.baemo.domains.exercise.domain.value.exercise.ExerciseId;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ExerciseCourt extends BaemoValidator {

    private final ExerciseCourtId id;
    @NotNull
    private final ExerciseId exerciseId;
    @NotNull
    @Setter
    private CourtNumber number;

    @Builder
    private ExerciseCourt(ExerciseCourtId id, ExerciseId exerciseId, CourtNumber number) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.number = number;
        valid();
    }
}
