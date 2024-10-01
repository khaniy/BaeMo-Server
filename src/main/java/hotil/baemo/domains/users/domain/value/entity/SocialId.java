package hotil.baemo.domains.users.domain.value.entity;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SocialId(
    @NotNull
    @Positive
    Long id
) {

    public SocialId(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}