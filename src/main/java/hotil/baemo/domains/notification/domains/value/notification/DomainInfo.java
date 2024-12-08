package hotil.baemo.domains.notification.domains.value.notification;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DomainInfo(
    @NotNull
    @Positive
    Long id
) {
    public DomainInfo(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
