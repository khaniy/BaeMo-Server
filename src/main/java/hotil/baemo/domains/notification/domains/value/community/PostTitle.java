package hotil.baemo.domains.notification.domains.value.community;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record PostTitle(
    @NotBlank
    String title
) {

    public PostTitle(String title) {
        this.title = title;
        BaemoValueObjectValidator.valid(this);
    }
}
