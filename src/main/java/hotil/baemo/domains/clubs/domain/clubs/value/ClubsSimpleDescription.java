package hotil.baemo.domains.clubs.domain.clubs.value;

import jakarta.validation.constraints.NotBlank;

public record ClubsSimpleDescription(
        @NotBlank
        String simpleDescription
) {
}
