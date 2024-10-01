package hotil.baemo.domains.clubs.domain.clubs.value;

import jakarta.validation.constraints.NotBlank;

public record ClubsName(
        @NotBlank
        String name
) {
}
