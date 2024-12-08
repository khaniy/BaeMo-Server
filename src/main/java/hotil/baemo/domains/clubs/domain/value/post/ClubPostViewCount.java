package hotil.baemo.domains.clubs.domain.value.post;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ClubPostViewCount(
    @NotNull
    @PositiveOrZero
    Long count
) {
}
