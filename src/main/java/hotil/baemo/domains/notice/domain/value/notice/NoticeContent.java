package hotil.baemo.domains.notice.domain.value.notice;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NoticeContent(
        @NotBlank
        @Size(min = 1, max = 30_000)
        String content
) {

    public NoticeContent(String content) {
        this.content = content;
        BaemoValueObjectValidator.valid(this);
    }
}