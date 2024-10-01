package hotil.baemo.domains.clubs.domain.clubs.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ClubsId(

        @NotNull
        @PositiveOrZero
        Long clubsId
) {
}
