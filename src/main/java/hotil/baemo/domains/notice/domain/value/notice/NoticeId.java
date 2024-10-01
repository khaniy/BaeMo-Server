package hotil.baemo.domains.notice.domain.value.notice;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record NoticeId(
    @NotNull
    @Positive
    Long id
) {
    public NoticeId(Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
