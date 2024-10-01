package hotil.baemo.domains.score.domain.aggregate;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import hotil.baemo.domains.score.domain.value.exercise.ExerciseUserRole;
import hotil.baemo.domains.score.domain.value.exercise.ExerciseUserStatus;
import hotil.baemo.domains.score.domain.value.user.UserId;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record ExerciseUser(
    @NotNull
    UserId id,
    @NotNull
    ExerciseUserRole role,
    @NotNull
    ExerciseUserStatus status
) {

    @Builder
    public ExerciseUser(UserId id, ExerciseUserRole role, ExerciseUserStatus status) {
        this.id = id;
        this.status = status;
        this.role = role;
        BaemoValueObjectValidator.valid(this);
    }
}
