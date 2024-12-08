package hotil.baemo.domains.clubs.domain.value.comment;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentContent(
        @NotBlank
        @Size(max = 1_000)
        String content
) {
    public CommentContent(String content) {
        this.content = content;
        BaemoValueObjectValidator.valid(this);
    }
}