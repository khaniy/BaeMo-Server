package hotil.baemo.domains.notice.domain.value.notice;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NoticeTitle(
        @NotBlank
        @Size(min = 1, max = 200)
        String title
) {

    public NoticeTitle(String title) {
        this.title = title;
        BaemoValueObjectValidator.valid(this);
    }
}