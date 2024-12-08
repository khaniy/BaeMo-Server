package hotil.baemo.domains.clubs.domain.entity.member;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ClubMemberId(
    @NotNull
    @PositiveOrZero
    Long id
) {
    public ClubMemberId(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
