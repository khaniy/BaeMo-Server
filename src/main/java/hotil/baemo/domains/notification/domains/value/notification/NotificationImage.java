package hotil.baemo.domains.notification.domains.value.notification;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record NotificationImage(
    @NotBlank
    String url
) {
    public NotificationImage(String url) {
        this.url = url;
        BaemoValueObjectValidator.valid(this);
    }
}
