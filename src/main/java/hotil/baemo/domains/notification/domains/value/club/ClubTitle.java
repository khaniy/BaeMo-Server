package hotil.baemo.domains.notification.domains.value.club;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record ClubTitle(
    @NotBlank
    String title
) {

    public ClubTitle(String title) {
        this.title = title;
        BaemoValueObjectValidator.valid(this);
    }
}
