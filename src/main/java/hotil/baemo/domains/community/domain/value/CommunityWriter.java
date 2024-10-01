package hotil.baemo.domains.community.domain.value;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CommunityWriter(
    @NotNull
    @Positive
    Long id
) {
    public CommunityWriter(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}