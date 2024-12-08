package hotil.baemo.domains.clubs.domain.value.post;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClubPostContent(
        @NotBlank
        @Size(min = 1, max = 30_000)
        String content
) {

    public ClubPostContent(String content) {
        this.content = content;
        BaemoValueObjectValidator.valid(this);
    }
}