package hotil.baemo.domains.clubs.domain.clubs.value;

import jakarta.validation.constraints.NotBlank;

public record ClubsDescription(
        @NotBlank
        String description
) {
}
