package hotil.baemo.domains.community.domain.entity;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CommunityId(
    @NotNull
    @Positive
    Long id
) {

    public CommunityId(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }

    public static CommunityId of(final Long id) {
        return new CommunityId(id);
    }
}
