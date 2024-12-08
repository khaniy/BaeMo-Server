package hotil.baemo.domains.clubs.domain.entity.club;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ClubId(

        @NotNull
        @PositiveOrZero
        Long clubsId
) {
}
