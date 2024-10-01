package hotil.baemo.domains.notice.domain.value.notice;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record NoticeView(
    @NotNull
    @PositiveOrZero
    Long view
) {
    public NoticeView(Long view) {
        this.view = view;
        BaemoValueObjectValidator.valid(this);
    }
}
