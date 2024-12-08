package hotil.baemo.domains.clubs.domain.value.club;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record ClubImageUrl(
        @NotBlank
        String url
) {
        public ClubImageUrl(String url) {
                this.url = url;
                BaemoValueObjectValidator.valid(this);
        }
}
