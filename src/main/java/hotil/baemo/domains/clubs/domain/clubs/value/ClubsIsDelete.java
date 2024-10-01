package hotil.baemo.domains.clubs.domain.clubs.value;

import jakarta.validation.constraints.NotNull;

public record ClubsIsDelete(
        @NotNull
        Boolean isDeleted
) {
}
