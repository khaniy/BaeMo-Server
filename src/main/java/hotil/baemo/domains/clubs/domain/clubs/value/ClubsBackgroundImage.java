package hotil.baemo.domains.clubs.domain.clubs.value;

import jakarta.validation.constraints.NotBlank;

public record ClubsBackgroundImage(
        @NotBlank
        String clubsBackgroundImage
) {
}
