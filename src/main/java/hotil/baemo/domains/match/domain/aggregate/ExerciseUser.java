package hotil.baemo.domains.match.domain.aggregate;

import hotil.baemo.core.validator.BaemoValidator;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseUserRole;
import hotil.baemo.domains.match.domain.value.exercise.ExerciseUserStatus;
import hotil.baemo.domains.match.domain.value.user.UserId;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ExerciseUser extends BaemoValidator {

    @NotNull
    private final UserId userId;
    @NotNull
    private final ExerciseUserRole role;
    @NotNull
    private final ExerciseUserStatus status;

    @Builder
    private ExerciseUser(UserId userId, ExerciseUserRole role, ExerciseUserStatus status) {
        this.userId = userId;
        this.role = role;
        this.status = status;
        valid();
    }
}
