package hotil.baemo.domains.notification.domains.value.notification;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record NotificationTitle(
    @NotBlank
    String title
) {
    public NotificationTitle(String title) {
        this.title = title;
        BaemoValueObjectValidator.valid(this);
    }
}
