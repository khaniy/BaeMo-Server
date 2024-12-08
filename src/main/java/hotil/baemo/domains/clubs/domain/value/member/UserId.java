package hotil.baemo.domains.clubs.domain.value.member;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UserId(
        @NotNull
        @PositiveOrZero
        Long id
) {
}
