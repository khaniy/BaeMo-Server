package hotil.baemo.domains.notification.domains.value.notification;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record NotificationBody(
    @NotBlank
    String body
) {
    public NotificationBody(String body) {
        this.body = body;
        BaemoValueObjectValidator.valid(this);
    }
}
