package hotil.baemo.domains.clubs.domain.value.club;

import jakarta.validation.constraints.NotBlank;

public record ClubDescription(
        @NotBlank
        String description
) {
}
