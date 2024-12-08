package hotil.baemo.domains.clubs.domain.value.club;

import jakarta.validation.constraints.NotBlank;

public record ClubName(
        @NotBlank
        String name
) {
}
