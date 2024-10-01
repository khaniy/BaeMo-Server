package hotil.baemo.domains.notice.domain.value.image;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record NoticeImageOrder(
    @NotNull
    @Positive
    Long order
) {
    public NoticeImageOrder(Long order) {
        this.order = order;
        BaemoValueObjectValidator.valid(this);
    }
}
