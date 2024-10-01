package hotil.baemo.domains.report.domain.value.post;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;

public record PostId(@NotNull Long id) {

    public PostId(@NotNull Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
