package hotil.baemo.domains.clubs.domain.replies.value;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RepliesContent(
        @NotBlank
        @Size(max = 1_000)
        String content
) {
    public RepliesContent(String content) {
        this.content = content;
        BaemoValueObjectValidator.valid(this);
    }
}