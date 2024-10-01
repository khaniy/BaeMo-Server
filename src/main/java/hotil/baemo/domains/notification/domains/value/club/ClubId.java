package hotil.baemo.domains.notification.domains.value.club;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ClubId(
    @NotNull
    @Positive
    Long id
) {
    public ClubId(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
