package hotil.baemo.domains.clubs.domain.value.comment;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentDepth(
        @NotNull
        Long depth
) {
    public CommentDepth(Long depth) {
        this.depth = depth;
        BaemoValueObjectValidator.valid(this);
    }
}