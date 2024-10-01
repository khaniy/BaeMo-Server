package hotil.baemo.domains.notification.domains.value.community;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record ThumbnailText(
    @NotBlank
    String text
) {

    public ThumbnailText(String text) {
        this.text = text;
        BaemoValueObjectValidator.valid(this);
    }
}
