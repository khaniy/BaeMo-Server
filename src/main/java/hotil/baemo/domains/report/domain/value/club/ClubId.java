package hotil.baemo.domains.report.domain.value.club;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;

public record ClubId(@NotNull Long id) {

    public ClubId(@NotNull Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
