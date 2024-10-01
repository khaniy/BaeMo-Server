package hotil.baemo.domains.community.domain.entity;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CommunityUserId(
    @NotNull
    @Positive
    Long id
) {
    public CommunityUserId(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
