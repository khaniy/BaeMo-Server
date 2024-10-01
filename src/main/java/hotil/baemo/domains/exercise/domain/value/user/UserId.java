package hotil.baemo.domains.exercise.domain.value.user;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UserId(
    @NotNull
    @Positive
    Long id
) {
    public UserId(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
