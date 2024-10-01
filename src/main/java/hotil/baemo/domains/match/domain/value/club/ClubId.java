package hotil.baemo.domains.match.domain.value.club;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record ClubId(
    @Positive
    @NotNull
    Long id
) {
    public ClubId(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
