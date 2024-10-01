package hotil.baemo.domains.notification.domains.value.notification;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record NotificationId(
    @NotNull
    @Positive
    Long id
) {
    public NotificationId(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
