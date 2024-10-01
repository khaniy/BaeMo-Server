package hotil.baemo.domains.relation.domain.value;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;

public record UserId(@NotNull Long id) {

    public UserId(@NotNull Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
