package hotil.baemo.domains.clubs.domain.clubs.value;

import jakarta.validation.constraints.NotBlank;

public record ClubsLocation(
        @NotBlank
        String location
) {
}
