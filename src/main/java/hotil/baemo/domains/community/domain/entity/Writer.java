package hotil.baemo.domains.community.domain.entity;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record Writer(
    @NotNull
    @Positive
    Long id
) {
    public Writer(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }

    public static Writer of(final Long id) {
        return new Writer(id);
    }
}